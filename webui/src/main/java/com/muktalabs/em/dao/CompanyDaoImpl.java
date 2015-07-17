package com.muktalabs.em.dao;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.muktalabs.em.model.Company;
import com.muktalabs.em.model.User;


@Service
public class CompanyDaoImpl extends HibernateDaoSupport implements CompanyDao {

    private static final Logger logger = Logger.getLogger(CompanyDaoImpl.class.getName());
    
    @Autowired
    public void autowireSessionFactory(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
        getHibernateTemplate().setCheckWriteOperations(false);
    }
    
    public String save(Company company) {

        if (StringUtils.isEmpty(company.getCompanyId())) {
            logger.info("Saving new company to db : " + company);
            String uuid = UUID.randomUUID().toString();
            company.setCompanyId(uuid);
            
            Serializable id = getHibernateTemplate().save(company);
            getHibernateTemplate().flush();
            return (String) id;
        } else {
            throw new RuntimeException("Record already exists (primary key present)");
        }
    }
    
    public String update(Company company) {

        if (! StringUtils.isEmpty(company.getCompanyId())) {
            logger.info("Updating company record in db : " + company);
            getHibernateTemplate().saveOrUpdate(company);
            getHibernateTemplate().flush();
            return company.getCompanyId();
        } else {
            throw new RuntimeException("Record does not exist (primary key not present)");
        }
    }

    public List<Company> list(int startIndex, int pageSize, User user ) {

        // TODO: handle startIndex, pageSize
        @SuppressWarnings("unchecked")
        List<Company> companyList = (List<Company>) getHibernateTemplate().find("from Company");
        return companyList;
    }

    public List<Company> list(Company criteria) {

        // TODO: add criteria
        @SuppressWarnings("unchecked")
        List<Company> companyList = (List<Company>) getHibernateTemplate().find("from Company");
        return companyList;
    }
    
    public List<Company> listByCompanyId(String companyId, User user){
        @SuppressWarnings("unchecked")
        List<Company> companyList = (List<Company>) getHibernateTemplate().find("from Company where companyId=? ", companyId);
        return companyList;
    }

    public Company getById(String id, User user) {

        Company company = getHibernateTemplate().get(Company.class, id);
        return company;
    }

    public String delete(String id, User user) {

        Company company = this.getById(id, user);
        getHibernateTemplate().delete(company);
        return id;
    }

}
