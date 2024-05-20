package persistence.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import persistence.jpa.mapper.GenericJpaMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public abstract class GenericCrudRepository<D, E, ID, R extends CrudRepository<E, ID>, M extends GenericJpaMapper<D, E>> {


    M mapper;
    R repository ;

    public GenericCrudRepository(M mapper, R repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public  D save(D domain) {
        E entity = this.getMapper().convertToJpa(domain);
        return  this.getMapper().convertToDomain(this.getRepository().save(entity));
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


    public long count() {
        return this.getRepository().count();
    }


    public void delete(D domain) {
        this.getRepository().delete(this.getMapper().convertToJpa(domain));
    }

     R getRepository(){
        return repository;
     };

    M getMapper(){
        return mapper;
    };


}
