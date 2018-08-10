package com.exigen.ldap.ws;

import com.exigen.ldap.type.GenericResponse;
import com.exigen.ldap.type.GenericResponseEntity;
import com.exigen.ldap.type.LdapInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * http://localhost:8080/ldapproxy/get?username=newton&password=password
 */
@RestController
public class FetchLDAPInformation {

    private static final Logger log = LoggerFactory.getLogger(FetchLDAPInformation.class);

    @RequestMapping(value = "/ldapproxy/get", method = RequestMethod.GET)
    public GenericResponse sendMessage(@RequestParam String username, @RequestParam String password) {

        GenericResponse response = new GenericResponse();

        final GenericResponseEntity responseEntity = new GenericResponseEntity();

        String user = null;
        try {
            Properties props = new Properties();
            props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            props.put(Context.PROVIDER_URL, "ldap://ldap.forumsys.com:389");
            props.put(Context.SECURITY_PRINCIPAL,
                      "cn=read-only-admin,dc=example,dc=com");//adminuser - User with special priviledge, dn user
            props.put(Context.SECURITY_CREDENTIALS, "password");//dn user password

            InitialDirContext context = new InitialDirContext(props);

            SearchControls ctrls = new SearchControls();
            ctrls.setReturningAttributes(new String[] {"givenName", "sn", "memberOf"});
            ctrls.setSearchScope(SearchControls.SUBTREE_SCOPE);

            String filter = "(&(objectClass=person)(uid=" + username + "))";

            NamingEnumeration<SearchResult> answers = context.search("dc=example,dc=com",  filter, getSimpleSearchControls());
            SearchResult result = null;
            try {
                result = answers.nextElement();
                NamingEnumeration namingEnumeration = result.getAttributes().getAll();
                final List<LdapInfo> ldapInfo = gatherAttrs(namingEnumeration);
                responseEntity.setData(ldapInfo);

            } catch (Exception e) {
                response.setStatus("500");
                responseEntity.setErrorTxt("Error, when fetching data");
                responseEntity.setErrorType("Internal server error");
                response.setEntity(responseEntity);
                return response;
            }
            ////////////////////////////////////////////////////////

            filter = "(uniquemember=uid="+username+",dc=example,dc=com)";
            answers = context.search("dc=example,dc=com",  filter, getSimpleSearchControls());
            result = null;
            try {
                result = answers.nextElement();
                NamingEnumeration namingEnumeration = result.getAttributes().getAll();
                final List<LdapInfo> ldapInfo = gatherAttrs(namingEnumeration);
                responseEntity.setOrgUnit(ldapInfo);

            } catch (Exception e) {
                response.setStatus("500");
                responseEntity.setErrorTxt("Error, when fetching data");
                responseEntity.setErrorType("Internal server error");
                response.setEntity(responseEntity);
                return response;
            }


        } catch (NamingException e) {
            e.printStackTrace();
        }


        response.setEntity(responseEntity);

        return response;
    }


    private SearchControls getSimpleSearchControls() {
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchControls.setTimeLimit(30000);
        return searchControls;
    }


    private List<LdapInfo> gatherAttrs(NamingEnumeration namingEnumeration) {
        List<LdapInfo> result = new ArrayList<>();

        if (namingEnumeration == null) {
            return result;
        } else {
        try {
          for (NamingEnumeration ae = namingEnumeration; ae.hasMore();) {
            Attribute attr = (Attribute) ae.next();

              LdapInfo element = new LdapInfo();
              element.setAttribute(attr.getID());

              List<String> value = new ArrayList<>();


              for (NamingEnumeration e = attr.getAll(); e.hasMore(); value.add(e.next().toString()));

              element.setValue(value);
              result.add(element);
        }
        } catch (NamingException e) {
          e.printStackTrace();
        }
      }
      return result;

    }

}
