package com.malo.library.persistence.jpa.repository;

import com.malo.library.exception.technical.TechnicalException;
import com.malo.library.exception.technical.TechnicalExceptionKey;
import com.malo.library.persistence.jpa.Identifiable;
import com.malo.library.persistence.jpa.Versioned;
import com.malo.library.persistence.jpa.entities.BorrowingJpa;
import com.malo.library.persistence.jpa.entities.BorrowingJpa_;
import com.malo.library.persistence.jpa.mapper.GenericJpaMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.data.repository.CrudRepository;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@SuppressWarnings("unused")
public abstract class GenericCrudRepository<D, E extends Identifiable<ID>, ID, R extends CrudRepository<E, ID>, M extends GenericJpaMapper<D, E>> {

    static final String FETCH_SIZE_HINT = "org.hibernate.fetchSize";

    static final String PERSISTENCE_FETCHGRAPH = "javax.persistence.fetchgraph";

    EntityManager entityManager;
    M mapper;
    R repository;

    public GenericCrudRepository(M mapper, R repository) {
        this.mapper = mapper;
        this.repository = repository;
    }


    public E save(E entity) {
        if(entity instanceof Versioned && Objects.nonNull(entity.getId()) && !Objects.equals(entity.getId(),0)){
            setOptimisticLock((Versioned) entity);
        }
        return this.getRepository().save(entity);
    }

    public E save(E entity, Consumer<E> setJoinedReference) {
        if (setJoinedReference != null) {
            setJoinedReference.accept(entity);
        }
        return save(entity);
    }

    public ID save(D domain) {
        E entity = this.getMapper().convertToJpa(domain);
        return save(entity).getId();
    }


    /**
     * Si vous devez attacher des références avant de persister l'entité,
     * redéfinissez la méthode save(domain) et appelez-la en fournissant un consommateur pour ajouter les références.
     *
     * @param domain l'objet de domaine à persister
     * @param setJoinedReference le consommateur pour ajouter les références jointes
     * @return l'ID de l'entité persistée
     *
     * Exemple d'utilisation :
     * <pre>
     * {@code
     * public class OrderJpa implements Identifiable<Long> {
     *     @Id
     *     @GeneratedValue
     *     Long id;
     *
     *     @ManyToOne
     *     CustomerJpa customer;
     *
     *     Long customerId;
     *
     *     // autres attributs et méthodes
     * }
     *
     * public class OrderRepository extends GenericCrudRepository<Order, OrderJpa, Long, JpaRepository<OrderJpa, Long>, OrderMapper> {
     *     public OrderRepository(OrderMapper mapper, JpaRepository<OrderJpa, Long> repository) {
     *         super(mapper, repository);
     *     }
     *
     *     @Override
     *     public Long save(Order domain) {
     *         return super.save(domain, orderJpa -> {
     *             CustomerJpa customerJpa = getEntityManager().getReference(CustomerJpa.class, orderJpa.getCustomerId());
     *             orderJpa.setCustomer(customerJpa);
     *         });
     *     }
     * }
     *
     * }
     * </pre>
     */
    public ID save(D domain, Consumer<E> setJoinedReference) {
        E entity = this.getMapper().convertToJpa(domain);
        return save(entity, setJoinedReference).getId();
    }

    public void saveAll(List<D> domains) {
        getRepository().saveAll(getMapper().convertToJpa(domains));
    }

    public Optional<D> findById(ID id) {
        return this.getRepository().findById(id).map(this.getMapper()::convertToDomain);
    }


    public boolean existsById(ID id) {
        return this.getRepository().existsById(id);
    }


    public List<D> findAll() {
        List<E> entities = StreamSupport
                .stream(this.getRepository().findAll().spliterator(), false)
                .collect(Collectors.toList());
        return this.getMapper().convertToDomain(entities);
    }

    public List<D> findAllByIds(List<ID> ids) {
        List<E> entities = StreamSupport
                .stream(this.getRepository().findAllById(ids).spliterator(), false)
                .collect(Collectors.toList());
        return this.getMapper().convertToDomain(entities);
    }


    public long count() {
        return this.getRepository().count();
    }


    public void delete(D domain) {
        this.getRepository().delete(this.getMapper().convertToJpa(domain));
    }

    public void deleteAll(List<D> domain) {
        this.getRepository().deleteAll(this.getMapper().convertToJpa(domain));
    }

    R getRepository() {
        return repository;
    }


    M getMapper() {
        return mapper;
    }

    @PersistenceContext
    void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    <T extends E> List<T> findByForeignKeyIn(Class<T> tClass, List<ForeignKeyIn<T>> conditions) {
        return findByForeignKeyIn(tClass, conditions, null);
    }

    <T extends E> List<T> findByForeignKeyIn(Class<T> tClass, List<ForeignKeyIn<T>> conditions, String entityGraph) {

        if (conditions.isEmpty()) {
            throw new TechnicalException(TechnicalExceptionKey.EMPTY_CONDITION_IN_FIND_BY_FOREIGN_KEY, new ArrayList<>());
        }
        CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(tClass);
        Root<T> root = cq.from(tClass);
        Predicate restrictions = conditions.stream().map(cd -> root.get(cd.attribute).in(cd.values)).reduce(cb::and).orElseThrow();
        cq.where(restrictions);

        if (StringUtils.hasLength(entityGraph)) {
            return getEntityManager().createQuery(cq).setHint(PERSISTENCE_FETCHGRAPH, entityGraph).getResultList();
        }
        return getEntityManager().createQuery(cq).getResultList();
    }

    protected <S,V> void deleteByIdAttribute(Class<S> clazz, Comparable<? super V> attributeValue, SingularAttribute<? super S, ? extends Comparable<?>> attribute) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaDelete<S> delete = cb.createCriteriaDelete(clazz);
        Root<S> root = delete.from(clazz);
        delete.where(cb.equal(root.get(attribute), attributeValue));
        getEntityManager().createQuery(delete).executeUpdate();
    }
  private void setOptimisticLock(Versioned entity){
       if (entity != null) {
           if (entity.getVersion() != null) {
               LockModeType currentLockMode = entityManager.getLockMode(entity);
               if (currentLockMode == null || currentLockMode == LockModeType.NONE) {
                   entityManager.lock(entity, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
               }
           }
       }
   }
    public static class ForeignKeyIn<E> {

        SingularAttribute<E, Long> attribute;
        Set<Long> values;
    }
}
