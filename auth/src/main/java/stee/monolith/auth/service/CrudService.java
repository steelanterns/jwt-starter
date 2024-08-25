package stee.monolith.auth.service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Steeve Jean Chilles on 09/22/2023
 */
public interface CrudService<T, ID> {
    List<T> findAll();

    Optional<T> findById(ID id);

    T save(T object);

    void delete(T object);

    void deleteById(ID id);


//    <S extends T> Iterable<S> saveAll(Iterable<S> entities);
//
//    boolean existsById(ID id);
//
//    Iterable<T> findAllById(Iterable<ID> ids);
//
//    long count();
//
//    void deleteAllById(Iterable<? extends ID> ids);
//
//    void deleteAll(Iterable<? extends T> entities);
//
//    void deleteAll();
}
