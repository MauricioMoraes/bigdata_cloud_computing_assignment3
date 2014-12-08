<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tags:layout activeTab="general_trends">

<div class="row">
	<div id="wordcloud" class="col-xs-12">
		
	</div>
</div>
</tags:layout>

<script type="text/javascript">

  var word_array = JSON.parse("${word_array}");

  $(function() {
    $("#wordcloud").jQCloud(word_array, {
      width: 1100,
      height: 600,
      delayedMode: true
    });
  });
</script>




