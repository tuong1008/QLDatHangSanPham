google.load('visualization', '1', { 'packages': ['geochart'] });


google.setOnLoadCallback(drawMap);

function drawMap() {
    var data = google.visualization.arrayToDataTable([
        ['Country', 'Amount'],
        ['VN', 2],
        ['DE', 1],
        ['JP', 2],
        ['US', 3],
        ['CN', 4],
    ]);

    var options = {
        title: 'Xuất xứ sản phẩm'
    };

    var geochart = new google.visualization.GeoChart(document.getElementById('chart_div'));

    geochart.draw(data, options);
}
