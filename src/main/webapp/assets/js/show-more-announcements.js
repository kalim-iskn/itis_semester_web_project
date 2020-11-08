let currentOffset = offset;
function showMore() {
    $(".load_more").val("Загрузка...");
    $(".load_more").prop("disabled", true);
    $.post(link, {offset: currentOffset}, function (data) {
        if (data === "error") {
            $(".load_more").hide();
            alert("Произошла ошибка с сервером или базой данных");
        } else if (data === "not-found") {
            $(".load_more").hide();
        } else {
            $(".load_more").val("Показать еще");
            $(".load_more").prop("disabled", false);
            $("#search").append(data);
            currentOffset += offset;
        }
    });
}