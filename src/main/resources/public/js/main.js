$(function() {
    console.log( "ready!" );
   var grid =  $('.events-grid').isotope({
        // options
        itemSelector: '.element-item',
        layoutMode: 'fitRows'
    });

    $('.filter-button-group').on( 'click', 'button', function() {
        var filterValue = $(this).attr('data-filter');
        grid.isotope({ filter: filterValue });
    });
});
