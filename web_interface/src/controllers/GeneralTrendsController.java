package controllers;

import helpers.WordcountHelper;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;

import controllers.utils.FlashMessages;

@WebServlet("/general_trends")
public class GeneralTrendsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GeneralTrendsController() {
        super();
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	FlashMessages.extractFlashMessages(request);
    	
    	request.setAttribute("word_array", StringEscapeUtils.escapeJson(WordcountHelper.getWordcountJsonArray("top_trends").toString()));
    	request.getRequestDispatcher("/WEB-INF/views/general_trends.jsp").forward(request, response);
	}
	
}
