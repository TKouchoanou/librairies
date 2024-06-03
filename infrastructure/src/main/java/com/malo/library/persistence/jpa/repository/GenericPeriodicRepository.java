package com.malo.library.persistence.jpa.repository;

import com.malo.library.exception.technical.TechnicalException;
import com.malo.library.exception.technical.TechnicalExceptionKey;
import com.malo.library.persistence.jpa.Identifiable;
import com.malo.library.persistence.jpa.entities.abstractEntity.PeriodicJpa;
import com.malo.library.persistence.jpa.entities.abstractEntity.PeriodicJpa_;
import com.malo.library.persistence.jpa.mapper.GenericJpaMapper;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings({"unused"})
public class GenericPeriodicRepository<D,E extends PeriodicJpa & Identifiable<ID>,ID,R extends JpaRepository<E,ID>,M extends GenericJpaMapper<D,E>> extends GenericCrudRepository<D,E,ID,R,M>{

  Class<E> clazz;
    public GenericPeriodicRepository(M mapper, R repository,Class<E> clazz) {
        super(mapper, repository);
        this.clazz = clazz;
    }

    <T extends E> List<T> findByForeignKeyInAndAtDate(Class<T> tClass, List<ForeignKeyIn<T>> conditions, LocalDateTime date) {
       return findByForeignKeyInAndAtDate(tClass,conditions,date,null);
    }
    <T extends E> List<T> findByForeignKeyInAndInScope(Class<T> tClass, List<ForeignKeyIn<T>> conditions, LocalDateTime startDate,LocalDateTime endDate) {
        return findByForeignKeyInAndInScope(tClass,conditions,startDate,endDate,null);

    }
    <T extends E> List<T> findByForeignKeyInAndInScope(Class<T> tClass, List<ForeignKeyIn<T>> conditions, LocalDateTime startDate,LocalDateTime endDate, String entityGraph) {
        if (conditions.isEmpty() || Objects.isNull(startDate) || Objects.isNull(endDate)) {
            throw new TechnicalException(TechnicalExceptionKey.EMPTY_CONDITION_IN_FIND_BY_FOREIGN_KEY, new ArrayList<>());
        }
        CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(tClass);
        Root<T> root = cq.from(tClass);

        Predicate finalPredicate= conditions.stream().map(cd -> root.get(cd.attribute).in(cd.values)).reduce(cb::and).orElseThrow();

        Predicate datePredicate = cb.and(
                    cb.greaterThanOrEqualTo(root.get(PeriodicJpa_.startDate),startDate),
                    cb.lessThanOrEqualTo(root.get(PeriodicJpa_.endDate),endDate)
            );

            finalPredicate = cb.and(finalPredicate,datePredicate);


        cq.where(finalPredicate);

        if (StringUtils.hasLength(entityGraph)) {
            return getEntityManager().createQuery(cq).setHint(PERSISTENCE_FETCHGRAPH, entityGraph).getResultList();
        }
        return getEntityManager().createQuery(cq).getResultList();
    }

    <T extends E> List<T> findByForeignKeyInAndAtDate(Class<T> tClass, List<ForeignKeyIn<T>> conditions, LocalDateTime date, String entityGraph) {
        if (conditions.isEmpty()) {
            throw new TechnicalException(TechnicalExceptionKey.EMPTY_CONDITION_IN_FIND_BY_FOREIGN_KEY, new ArrayList<>());
        }
        CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(tClass);
        Root<T> root = cq.from(tClass);

        Predicate finalPredicate= conditions.stream().map(cd -> root.get(cd.attribute).in(cd.values)).reduce(cb::and).orElseThrow();
        if(date!=null){
            Predicate datePredicate = cb.and(
                    cb.greaterThanOrEqualTo(root.get(PeriodicJpa_.startDate),date),
                    cb.lessThanOrEqualTo(root.get(PeriodicJpa_.endDate),date)
            );
            finalPredicate = cb.and(finalPredicate,datePredicate);
        }

        cq.where(finalPredicate);

        if (StringUtils.hasLength(entityGraph)) {
            return getEntityManager().createQuery(cq).setHint(PERSISTENCE_FETCHGRAPH, entityGraph).getResultList();
        }
        return getEntityManager().createQuery(cq).getResultList();
    }

     <T extends E>List<T> findAllInScope(Class<T> tClass,LocalDateTime startDate, LocalDateTime endDate) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(tClass);
        Root<T> root = query.from(tClass);

        query.where(cb.and(
                cb.lessThanOrEqualTo(root.get(PeriodicJpa_.startDate), startDate),
                cb.greaterThanOrEqualTo(root.get(PeriodicJpa_.endDate), endDate)
        ));

        return getEntityManager().createQuery(query).getResultList();
    }

    <T extends E> List<T> findAllAtDate(Class<T> tClass, LocalDateTime date) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(tClass);
        Root<T> root = query.from(tClass);

        query.where(cb.and(
                cb.lessThanOrEqualTo(root.get(PeriodicJpa_.startDate), date),
                cb.greaterThanOrEqualTo(root.get(PeriodicJpa_.endDate), date)
        ));

        return getEntityManager().createQuery(query).getResultList();
    }

    <T extends E> List<T> findByIdsInScope(Class<T> tClass, Set<ID> ids, LocalDateTime startDate, LocalDateTime endDate,SingularAttribute<T,ID> tidSingularAttribute) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(tClass);
        Root<T> root = query.from(tClass);

        Predicate idPredicate = root.get(tidSingularAttribute).in(ids);
        Predicate scopePredicate = cb.and(
                cb.lessThanOrEqualTo(root.get(PeriodicJpa_.startDate), startDate),
                cb.greaterThanOrEqualTo(root.get(PeriodicJpa_.endDate), endDate)
        );

        query.where(cb.and(idPredicate, scopePredicate));

        return getEntityManager().createQuery(query).getResultList();
    }

    <T extends E> List<T> findByIdsAtDate(Class<T> tClass, Set<ID> ids, LocalDateTime date, SingularAttribute<T,ID> tidSingularAttribute) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(tClass);
        Root<T> root = query.from(tClass);

        Predicate idPredicate = root.get(tidSingularAttribute).in(ids);
        Predicate datePredicate = cb.and(
                cb.lessThanOrEqualTo(root.get(PeriodicJpa_.startDate), date),
                cb.greaterThanOrEqualTo(root.get(PeriodicJpa_.endDate), date)
        );

        query.where(cb.and(idPredicate, datePredicate));

        return getEntityManager().createQuery(query).getResultList();
    }


}
