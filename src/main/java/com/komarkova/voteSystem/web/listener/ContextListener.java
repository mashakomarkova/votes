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

        Thread schedulerElection = new Thread(() -> {
            while (true){
                try {
                    DBManager.getInstance().
                            deleteElection();
                    Thread.sleep(50400000);
                } catch (InterruptedException | DBException ignored) {
                }
            }
        });
        schedulerElection.start();

        Thread schedulerTopElectionToPlain = new Thread(()->{
            while (true){
                try{
                    DBManager.getInstance().updateElectionToPlain();
                    Thread.sleep(50400000);
                } catch (DBException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        schedulerTopElectionToPlain.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
