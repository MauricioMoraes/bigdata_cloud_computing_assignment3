<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tags:layout activeTab="timeseries">

<div class="row">
	<div id="chart-container" class="col-xs-12" style="min-width: 1100px; height: 600px; margin: 0 auto">
	</div>
</div>
</tags:layout>

<script type="text/javascript">

var data_array = JSON.parse("${data_array}");

$(function () {
    $('#chart-container').highcharts({
        chart: {
            zoomType: 'x'
        },
        title: {
            text: 'Tweet Sentiment Timeseries'
        },
        subtitle: {
            text: document.ontouchstart === undefined ?
                    'Click and drag in the plot area to zoom in' :
                    'Pinch the chart to zoom in'
        },
        xAxis: {
            type: 'datetime',
            minRange: 14 * 3600000 // fourteen hours
        },
        yAxis: {
            title: {
                text: 'Sentiment (from -1 to 1)'
            }
        },
        legend: {
            enabled: false
        },
        plotOptions: {
            area: {
                fillColor: {
                    linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
                    stops: [
                        [0, Highcharts.getOptions().colors[0]],
                        [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0.5).get('rgba')]
                    ]
                },
                negativeFillColor: {
                    linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
                    stops: [
                        [0, Highcharts.getOptions().colors[3]],
                        [1, Highcharts.Color(Highcharts.getOptions().colors[3]).setOpacity(0.5).get('rgba')]
                    ]
                },
                marker: {
                    radius: 2
                },
                lineWidth: 1,
                states: {
                    hover: {
                        lineWidth: 1
                    }
                },
                threshold: null
            }
        },

        series: [{
            type: 'area',
            name: 'Sentiment score (-1 to 1)',
            pointInterval: 3600 * 1000,
            pointStart: Date.UTC(2014, 3, 21, 19, 43),
            data: data_array
        }]
    });
});

</script>




