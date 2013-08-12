var EarningsReport = {
    options: {
        lines: {
            show: true
        },
        points: {
            show: true
        },
        xaxis: {
            tickDecimals: 0,
            tickSize: 1
        }
    },
    url: "/reports/earnings",
    data: [],
    fetchedData: {},
    prevSelected: 3,
    load: function (period) {
        var scope = this;
        $.plot("#placeholder", this.data, options);
        period = !period || period == 0 ? 3 : period;
        var prevSelector = $("#period_" + this.prevSelected);
        var nextSelector = $("#period_" + period);
        if (prevSelector) prevSelector.removeClass("active");
        if (nextSelector) nextSelector.addClass("active");
        this.prevSelected = period;
        $.ajax({
            url: scope.url + "/" + period,
            type: "GET",
            dataType: "json",
            success: function (report) {
                if (!scope.fetchedData[report.label]) {
                    scope.fetchedData[report.label] = true;
                    scope.data.push(report);
                }
                $.plot("#placeholder", scope.data, scope.options);
                scope.fetchedData = {};
                scope.data = [];
            }
        });
    }
};
$(document).ready(function () {
    EarningsReport.load();
});