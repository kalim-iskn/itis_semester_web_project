let isOpened = false;
$(".extend-search-btn").click(function(){
    if (isOpened)
        $(".extend-search").hide();
    else
        $(".extend-search").show();
    isOpened = !isOpened;
})
