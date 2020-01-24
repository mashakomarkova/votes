package com.komarkova.voteSystem.web.filter;

import com.komarkova.voteSystem.Path;
import com.komarkova.voteSystem.db.Role;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@WebFilter(filterName = "CommandAccessFilter", initParams = {@WebInitParam(name = "admin", value = "deleteElectionAdmin " +
        "deleteUserById manageUsers userElections"), @WebInitParam(name = "client", value = "createElectionCommand " +
        "addToFavorites " +
        "createElectionForm payment search sort favoriteElections myTransactions deleteElection " +
        "findMyElectionById findElectionById myElections participateInElection viewPollResults updateElection " +
        "viewAllElections pageElections"),
        @WebInitParam(name = "common", value = "updatePicture updateEm updatePass updateSettings viewSettings logout"),
        @WebInitParam(name = "out-of-control", value = "updateUserRest " +
                "viewMyTransactionsRest viewMyElectionRest viewMyElectionsRest " +
                "participateRest " +
                "findElectionRest viewAllElectionsRest cer findU successLanguage" +
                " changeLocale confirmEmail login register")},
        urlPatterns = "/controller")
public class CommandAccessFilter implements Filter {

    private Map<Role, List<String>> accessMap = new HashMap<Role, List<String>>();
    private List<String> commons = new ArrayList<String>();
    private List<String> outOfControl = new ArrayList<String>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        accessMap.put(Role.CLIENT, asList(filterConfig.getInitParameter("client")));
        accessMap.put(Role.ADMIN, asList(filterConfig.getInitParameter("admin")));

        // commons
        commons = asList(filterConfig.getInitParameter("common"));

        // out of control
        outOfControl = asList(filterConfig.getInitParameter("out-of-control"));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (accessAllowed(servletRequest)) {

            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String errorMessasge = "You do not have permission to access the requested resource";

            servletRequest.setAttribute("errorMessage", errorMessasge);

            servletRequest.getRequestDispatcher(Path.PAGE_ERROR_PAGE)
                    .forward(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
    private boolean accessAllowed(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String commandName = request.getParameter("command");
        if (commandName == null || commandName.isEmpty()) {
            return false;
        }
        if (outOfControl.contains(commandName)) {
            return true;
        }
        HttpSession session = httpRequest.getSession(false);
        if (session == null) {
            return false;
        }
        Role userRole = (Role) session.getAttribute("userRole");
        if (userRole == null) {
            return false;
        }
        System.out.println(session.getAttribute("userRole"));
        System.out.println(accessMap.get(userRole).contains(commandName));
        System.out.println(commons.contains(commandName));
        System.out.println(accessMap);
        System.out.println(commandName);
        return accessMap.get(userRole).contains(commandName)
                || commons.contains(commandName);

    }

    /**
     * Extracts parameter values from string.
     *
     * @param str
     *            parameter values string.
     * @return list of parameter values.
     */
    private List<String> asList(String str) {
        List<String> list = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(str);
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
        return list;
    }
}
