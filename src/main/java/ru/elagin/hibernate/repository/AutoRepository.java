package ru.elagin.hibernate.repository;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import ru.elagin.hibernate.dto.AutoFilter;
import ru.elagin.hibernate.models.Auto;
import ru.elagin.hibernate.utils.HibernateSessionFactoryUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AutoRepository implements Repository<Integer, Auto, AutoFilter> {

    @Override
    public List<Auto> index() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<Auto> autos = session.createQuery("SELECT a FROM Auto a", Auto.class).getResultList();
        transaction.commit();
        session.close();
        return autos;
    }

    @Override
    public List<Auto> index(AutoFilter filter) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        List<String> whereSql = new ArrayList<>();
        Map<String, Object> mapParameters = new HashMap<>();

        if (filter.getModel() != null) {
            whereSql.add("a.model LIKE :model");
            mapParameters.put("model", ("%" + filter.getModel() + "%"));
        }
        if (filter.getColor() != null) {
            whereSql.add("a.color LIKE :color");
            mapParameters.put("color", ("%" + filter.getColor() + "%"));
        }
        if (filter.getYear() != null) {
            whereSql.add("a.year = :year");
            mapParameters.put("year", filter.getYear());
        }
        if (filter.getLimit() != null) {
            mapParameters.put("limit", filter.getLimit());
        }
        if (filter.getOffset() != null) {
            mapParameters.put("offset", filter.getOffset());
        }

        String where;
        if (whereSql.isEmpty()) {
            where = " ORDER BY a.customer, a.id";
        } else {
            where = whereSql.stream().collect(Collectors.joining
                    (" AND ", " WHERE ", " ORDER BY a.customer, a.id"));
        }

        Query<Auto> query = session.createQuery("FROM Auto a" + where, Auto.class);

        mapParameters.forEach((s, o) -> {
            if ("limit".equals(s))
                query.setMaxResults((Integer) o);
            else if ("offset".equals(s))
                query.setFirstResult((Integer) o);
            else
                query.setParameter(s, o);
        });

        List<Auto> autos = query.getResultList();
        transaction.commit();
        session.close();
        return autos;
    }

    @Override
    public Auto show(Integer id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Auto auto = session.get(Auto.class, id);
        transaction.commit();
        Hibernate.initialize(auto.getCustomer());
        session.close();
        return auto;
    }

    @Override
    public void save(Auto auto) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        if (auto.getCustomer().getId() == null)
            auto.setCustomer(null);
        session.save(auto);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Integer id, Auto updatedAuto) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Auto autoFromDb = session.get(Auto.class, id);
        autoFromDb.setModel(updatedAuto.getModel());
        autoFromDb.setColor(updatedAuto.getColor());
        autoFromDb.setYear(updatedAuto.getYear());
        if (updatedAuto.getCustomer().getId() == null)
            updatedAuto.setCustomer(null);
        autoFromDb.setCustomer(updatedAuto.getCustomer());
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Integer id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Auto auto = session.get(Auto.class, id);
        Transaction transaction = session.beginTransaction();
        session.delete(auto);
        transaction.commit();
        session.close();
    }
}
