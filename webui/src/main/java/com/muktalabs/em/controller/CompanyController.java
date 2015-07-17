package com.muktalabs.em.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.muktalabs.em.controller.json.ResponseCode;
import com.muktalabs.em.model.Company;
import com.muktalabs.em.model.User;
import com.muktalabs.em.service.CompanyService;
import com.muktalabs.em.util.LoginCheckUtil;
import com.muktalabs.em.util.StringUtils;


@RestController
@RequestMapping("/company/")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    private static final Logger logger = Logger.getLogger(CompanyController.class.getName());

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getView(@ModelAttribute Company company) {
        return new ModelAndView("list_company_master");
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody List<Company> getAllCompanys(@RequestParam int jtStartIndex, @RequestParam int jtPageSize
            ,HttpSession session) {

        logger.info("Start getCompany. jtStartIndex=" + jtStartIndex + ", jtPageSize=" + jtPageSize);
        List<Company> response = new ArrayList<Company>();
        boolean check = LoginCheckUtil.loginCheck();
        User user = (User) session.getAttribute("logged_in_user");
        if(check){
            response = companyService.list(jtStartIndex, jtPageSize, user);       
            return response;
        }
        else{
            Company el = new Company();
            el.error  = "nouser";
            response.add(el);
        }
        return response;
    }
    
    @RequestMapping(value = "/listByCompanyId/{id}", method = RequestMethod.GET)
    public @ResponseBody List<Company> getCompanysByCompanyId(@PathVariable("id")  String companyId
            ,HttpSession session) {

        logger.info("Start getCompanys. by companyId=" + companyId);
        List<Company> response = new ArrayList<Company>();
        boolean check = LoginCheckUtil.loginCheck();
        User user = (User) session.getAttribute("logged_in_user");
        if(check){
        try {
            response = companyService.listByCompanyId(companyId, user);
            } catch (Exception ex) {
            logger.log(Level.WARNING, "Exception while retriving employer Heads: ", ex);         
            }        
        }
        else{
            Company el = new Company();
            el.error  = "nouser";
            response.add(el);
        }
       return response;
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public @ResponseBody Company getCompany(@PathVariable("id") String headID, HttpSession session) {

        logger.info("Start getCompany. ID=" + headID);
        boolean check = LoginCheckUtil.loginCheck();
        User user = (User) session.getAttribute("logged_in_user");
        if(check){
        return companyService.getById(headID, user);
        }
        else{
            Company el =new Company();
            el.error = "nouser";
            return el;
        }        
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createCompany(@ModelAttribute Company company, BindingResult result,
            HttpSession session) {

        String response = ResponseCode.SUCCESS;
        logger.info("Start createCompany.");
        boolean check = LoginCheckUtil.loginCheck();
       if(check){
        if (result.hasErrors()) {
            logger.info("Data binding errors: " + result);
            response = ResponseCode.ERROR + "Data binding errors";
        } else {
            try {
                companyService.save(company);
            } catch (Exception ex) {
                logger.log(Level.WARNING, "Exception while saving emploer: ", ex);
                response = ResponseCode.ERROR + ex.getMessage();
            }
        }
        return response;
        }
       else {
        return "nouser";
    }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateCompany(@ModelAttribute Company company, BindingResult result,
            HttpSession session){

        logger.info("Start updateCompany.");
        String response = ResponseCode.SUCCESS;
        boolean check = LoginCheckUtil.loginCheck();
        if(check){
        if (result.hasErrors()) {
            response = ResponseCode.ERROR + "Binding Errors";
        } else {
            try {
                if(! StringUtils.isUUID(company.getCompanyId())){
                    throw new RuntimeException("Incorrect primary key : " + company.getCompanyId());
                }
                User user = (User) session.getAttribute("logged_in_user");
                Company existing = companyService.getById(company.getCompanyId(), user);
               // existing.update(company);
               
                if (user != null) {
                   // existing.setModifiedBy(user.getUserId());
                    
                }
                //existing.setModifiedOn(new Date());

                companyService.update(existing);
            } catch (Exception ex) {
                logger.log(Level.WARNING, "Exception while updating company: ", ex);
                response = ResponseCode.ERROR + ex.getMessage();
            }
        }
        return response;
       }
        else{
            return "nouser";
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public @ResponseBody String deleteCompany(@PathVariable("id") String headID, HttpSession session) {

        logger.info("Start deleteCompany.");
        String response = ResponseCode.SUCCESS;
        boolean check = LoginCheckUtil.loginCheck();
        User user = (User) session.getAttribute("logged_in_user");
        if(check){
        try {
            companyService.delete(headID, user);
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Exception while deleting company: ", ex);
            response = ResponseCode.ERROR + ex.getMessage();
        }
        return response;
       }
        else{
            return "nouser";
        }
    }
}
