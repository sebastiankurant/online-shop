$(function() {
    console.log( "ready!" );
   var grid =  $('.products-grid').isotope({
        // options
        itemSelector: '.element-item',
        layoutMode: 'fitRows'
    });

    $('.filter-button-group').on( 'click', 'button', function() {
        var filterValue = $(this).attr('data-filter');
        grid.isotope({ filter: filterValue });
    });

    $('.carousel').carousel()
});
