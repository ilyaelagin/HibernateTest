package ru.elagin.hibernate.repository;

import java.util.List;

public interface Repository<K, M, F> {

    List<M> index();

    List<M> index(F filter);

    M show(K id);

    void save(M m);

    void update(K id, M m);

    void delete(K id);
}
