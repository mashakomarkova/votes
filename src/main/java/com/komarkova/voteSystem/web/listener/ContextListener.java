package com.komarkova.voteSystem.web.listener;

import com.komarkova.voteSystem.db.DBManager;
import com.komarkova.voteSystem.exception.DBException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        Thread schedulerElection = new Thread(){
            @Override
            public void run() {
                while (true){
                    try {
                        DBManager.getInstance().
                                deleteElection();
                        Thread.sleep(50400000);
                    } catch (InterruptedException | DBException e) {
                    }
                }
            }
        };
        schedulerElection.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
