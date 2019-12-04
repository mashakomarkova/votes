package com.komarkova.voteSystem.web.command;

import com.komarkova.voteSystem.web.command.admin.DeleteElectionAdminCommand;
import com.komarkova.voteSystem.web.command.admin.DeleteUserByIdCommand;
import com.komarkova.voteSystem.web.command.admin.ManageUsersCommand;
import com.komarkova.voteSystem.web.command.admin.ViewUserElectionCommand;
import com.komarkova.voteSystem.web.command.client.*;
import com.komarkova.voteSystem.web.command.common.*;

import java.util.Map;
import java.util.TreeMap;

/**
 * Holder for all commands.
 */
public class CommandContainer {
    private static Map<String, Command> commands = new TreeMap<String, Command>();

    static {
        commands.put("register", new RegisterCommand());
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("viewSettings", new ViewSettingsCommand());
        commands.put("changeLocale", new ChangeLocaleCommand());
        commands.put("updateEm", new UpdateEmailCommand());
        commands.put("updatePass", new UpdatePasswordCommand());
        commands.put("updateSettings", new UpdateSettings());
        commands.put("createElectionForm", new CreateElectionFormCommand());
        commands.put("createElection", new CreateElectionCommand());
        commands.put("viewAllElections", new ViewAllElectionsCommand());
        commands.put("findElectionById", new FindElectionCommand());
        commands.put("participateInElection", new ParticipateCommand());
        commands.put("myElections", new MyElectionsCommand());
        commands.put("findMyElectionById", new EditMyElectionCommand());
        commands.put("deleteElection", new DeleteElectionCommand());
        commands.put("updateElection", new UpdateElectionCommand());
        commands.put("viewPollResults", new PollResultCommand());
        commands.put("payment", new PayForTopCommand());
        commands.put("search", new SearchCommand());
        commands.put("myTransactions", new ViewMyTransactionsCommand());
        commands.put("sort", new SortElectionsCommand());
        commands.put("addToFavorites", new AddFavoritesCommand());
        commands.put("favoriteElections", new ViewFavoriteElections());
        commands.put("confirmEmail", new ConfirmEmailCommand());
        commands.put("pageElections", new ViewElectionsByPageCommand());
        commands.put("manageUsers", new ManageUsersCommand());
        commands.put("deleteUserById", new DeleteUserByIdCommand());
        commands.put("userElections", new ViewUserElectionCommand());
        commands.put("deleteElectionAdmin", new DeleteElectionAdminCommand());
    }
    /**
     * Returns command object with the given name.
     *
     * @param commandName
     *            Name of the command.
     * @return Command object.
     */
    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            return commands.get("noCommand");
        }
        return commands.get(commandName);
    }
}
