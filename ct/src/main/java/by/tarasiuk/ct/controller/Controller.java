package by.tarasiuk.ct.controller;

import by.tarasiuk.ct.command.Command;
import by.tarasiuk.ct.command.CommandProvider;
import by.tarasiuk.ct.manager.PagePath;
import by.tarasiuk.ct.model.connection.ConnectionPool;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Optional;

/**
 * Controller - the main application servlet that processing all requests to the server and forms responses.
 */
@WebServlet(urlPatterns = {"/controller"})
public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final long serialVersionUID = -5845944618545604913L;

    /**
     * Default GET method.
     *
     * @param request HttpServletRequest.
     * @param response HttpServletResponse.
     * @throws ServletException general exception of a servlet can throw when it encounters difficulty.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Default GET method
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException general exception of a servlet can throw when it encounters difficulty.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Definition of a command from JSP
        Optional<Command> optionalCommand = CommandProvider.defineCommand(request);
        String pagePath;

        if(optionalCommand.isPresent()) {
            Command command = optionalCommand.get();

            RequestContent requestContent = new RequestContent();
            requestContent.buildContent(request);
            pagePath = command.execute(requestContent);
            requestContent.restore(request);

            RequestDispatcher dispatcher = request.getRequestDispatcher(pagePath);
            dispatcher.forward(request, response);
        } else {
            LOGGER.info("Forward to: {}.", PagePath.MAIN);
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + PagePath.MAIN);
        }
    }

    /**
     * If application stops, try to
     * shutdown {@link by.tarasiuk.ct.model.connection.ConnectionPool}
     */
    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroyPool();
    }
}
