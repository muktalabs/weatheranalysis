package com.muktalabs.em.service;

import java.util.List;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.muktalabs.em.dao.CompanyDao;
import com.muktalabs.em.model.Company;
import com.muktalabs.em.model.User;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyDao companyDao;
    
    private static final Logger logger = Logger.getLogger(CompanyServiceImpl.class.getName());

    @Transactional
    public String save(Company company) {
        logger.info("Saving company : " + company);
        return companyDao.save(company);
    }
    
    @Transactional
    public String update(Company company) {
        logger.info("Updating company : " + company);
        return companyDao.update(company);
    }

    public List<Company> list(Company criteria) {
        return companyDao.list(criteria);
    }

    public Company getById(String id, User user) {
        return companyDao.getById(id, user);
    }
    
    public List<Company> listByCompanyId(String companyId, User user){
        return companyDao.listByCompanyId(companyId, user);
    }

    public String delete(String id, User user) {
        return companyDao.delete(id, user);
    }

    public List<Company> list(int startIndex, int pageSize, User user) {
        return companyDao.list(startIndex, pageSize, user);
    }

}