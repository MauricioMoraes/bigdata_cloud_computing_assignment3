<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tags:layout activeTab="positive_negative">

<div class="row">
	<div id="positive-wordcloud" class="col-xs-12 col-md-6">
		
	</div>
	<div id="negative-wordcloud" class="col-xs-12 col-md-6">
		
	</div>
</div>
</tags:layout>

<script type="text/javascript">

  var positive_word_array = JSON.parse("${positive_word_array}");
  var negative_word_array = JSON.parse("${negative_word_array}");

  $(function() {
    $("#positive-wordcloud").jQCloud(positive_word_array, {
      width: 550,
      height: 600,
      delayedMode: true
    });
    $("#negative-wordcloud").jQCloud(negative_word_array, {
      width: 550,
      height: 600,
      delayedMode: true
    });
  });
</script>




