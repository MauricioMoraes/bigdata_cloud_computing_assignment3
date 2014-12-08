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

@WebServlet("/positive_negative")
public class PositiveNegativeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PositiveNegativeController() {
        super();
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	FlashMessages.extractFlashMessages(request);
    	
    	request.setAttribute("positive_word_array", StringEscapeUtils.escapeJson(WordcountHelper.getWordcountJsonArray("positive_trends").toString()));
    	request.setAttribute("negative_word_array", StringEscapeUtils.escapeJson(WordcountHelper.getWordcountJsonArray("negative_trends").toString()));
    	request.getRequestDispatcher("/WEB-INF/views/positive_negative.jsp").forward(request, response);
	}
	
}
