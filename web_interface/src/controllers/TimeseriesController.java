package controllers;

import helpers.SentimentHelper;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;

import controllers.utils.FlashMessages;

@WebServlet("/timeseries")
public class TimeseriesController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public TimeseriesController() {
        super();
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	FlashMessages.extractFlashMessages(request);
    	
    	request.setAttribute("data_array", StringEscapeUtils.escapeJson(SentimentHelper.getSentimentDataArray().toString()));
    	request.getRequestDispatcher("/WEB-INF/views/timeseries.jsp").forward(request, response);
	}
	
}
