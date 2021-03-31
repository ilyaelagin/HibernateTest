package ru.elagin.hibernate.repository;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import ru.elagin.hibernate.dto.CustomerFilter;
import ru.elagin.hibernate.models.Customer;
import ru.elagin.hibernate.utils.HibernateSessionFactoryUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CustomerRepository implements Repository<Integer, Customer, CustomerFilter> {

    @Override
    public List<Customer> index() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            List<Customer> customers = session.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
            transaction.commit();
            return customers;
        }
    }

    @Override
    public List<Customer> index(CustomerFilter filter) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            List<String> whereSql = new ArrayList<>();
            Map<String, Object> mapParameters = new HashMap<>();

            if (filter.getTabnum() != null) {
                whereSql.add("c.tabnum = :tabnum");
                mapParameters.put("tabnum", filter.getTabnum());
            }
            if (filter.getName() != null) {
                whereSql.add("c.name LIKE :name");
                mapParameters.put("name", ("%" + filter.getName() + "%"));
            }
            if (filter.getSurname() != null) {
                whereSql.add("c.surname LIKE :surname");
                mapParameters.put("surname", ("%" + filter.getSurname() + "%"));
            }
            if (filter.getEmail() != null) {
                whereSql.add("c.email LIKE :email");
                mapParameters.put("email", ("%" + filter.getEmail() + "%"));
            }
            if (filter.getBirth() != null) {
                whereSql.add("c.birth = :birth");
                mapParameters.put("birth", filter.getBirth());
            }
            if (filter.getLimit() != null) {
                mapParameters.put("limit", filter.getLimit());
            }
            if (filter.getOffset() != null) {
                mapParameters.put("offset", filter.getOffset());
            }

            String where;
            if (whereSql.isEmpty()) {
                where = " ORDER BY c.id";
            } else {
                where = whereSql.stream().collect(Collectors.joining
                        (" AND ", " WHERE ", " ORDER BY c.id"));
            }

            Query<Customer> query = session.createQuery("SELECT c FROM Customer c " + where, Customer.class);

            mapParameters.forEach((s, o) -> {
                if ("limit".equals(s))
                    query.setMaxResults((Integer) o);
                else if ("offset".equals(s))
                    query.setFirstResult((Integer) o);
                else
                    query.setParameter(s, o);
            });

            List<Customer> customers = query.getResultList();
            transaction.commit();
            customers.forEach(customer -> Hibernate.initialize(customer.getAutos()));
            return customers;
        }
    }

    @Override
    public Customer show(Integer id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Customer customer = session.get(Customer.class, id);
            transaction.commit();
            if (customer != null)
                Hibernate.initialize(customer.getAutos());
            return customer;
        }
    }

    @Override
    public void save(Customer customer) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(customer);
            transaction.commit();
        }
    }

    @Override
    public void update(Integer id, Customer updatedCustomer) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Customer customerFromDb = session.get(Customer.class, id);
            customerFromDb.setTabnum(updatedCustomer.getTabnum());
            customerFromDb.setName(updatedCustomer.getName());
            customerFromDb.setSurname(updatedCustomer.getSurname());
            customerFromDb.setEmail(updatedCustomer.getEmail());
            customerFromDb.setBirth(updatedCustomer.getBirth());
            customerFromDb.setAutos(updatedCustomer.getAutos());
            transaction.commit();
        }
    }

    @Override
    public void delete(Integer id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Customer customer = session.get(Customer.class, id);
            Transaction transaction = session.beginTransaction();
            session.delete(customer);
            transaction.commit();
        }
    }
}
