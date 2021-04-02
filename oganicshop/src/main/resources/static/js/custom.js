$(document).ready(function () {

    loadHeaderCart();

    /* function add item in cart using jquery ajax  */
    $('#add-cart').on('click', function () {
        let data = {
            productUrl: $(this).attr("data-productUrl"),
            quantity: $('input[class="input-number"]').val()
        };

        $.when(
            $.ajax({
                url: "/api/cart/add",
                type: "POST",
                data: JSON.stringify(data),
                dataType: 'json',
                contentType: 'application/json',
                success: function (result) {
                    $(".quickview-content .error-msg").remove();
                    if (result === false) {
                        $(".quickview-content .add-to-cart").after("<div class='error-msg' style='color: red;'>Không đủ số lượng để cung cấp thêm</div>");
                    } else {
                        alert("sản phẩm đã được thêm vào giỏ hàng");
                    }
                }
            })
        ).then(function () {
            loadHeaderCart();
        });
    });
    /* end function add item in cart */

    /* function minus item in cart using jquery ajax  */
    $('.shopping-cart .qty .button.minus button').on("click", function () {
        let buttonMinus = $(this);
        let quantity = buttonMinus.parent().siblings('input').val();
        let unitPrice = buttonMinus.closest('tr').find('td.price span').text();
        let totalAmountItem = parseInt(quantity) * parseInt(unitPrice);
        let data = {
            productUrl : buttonMinus.closest('tr').attr('data-productUrl'),
            changeMethod : "minus"
        }
        if (quantity > 0) {
            $.when(
                $.ajax({
                    url: "/api/cart/edit",
                    type: "PUT",
                    data: JSON.stringify(data),
                    dataType: 'json',
                    contentType: 'application/json',
                    success: function (result) {
                        $(".quickview-content .error-msg").remove();
                        if (result === false) {
                            $(".quickview-content .add-to-cart").after("<div class='error-msg' style='color: red;'>Không đủ số lượng để cung cấp thêm</div>");
                        } else {
                            buttonMinus.closest('tr').find('td.total-amount span').text(totalAmountItem);
                        }
                    }
                })
            ).then(function () {
                loadHeaderCart();
                updatePriceCart()
            });
        }
    });
    /* end function minus item in cart */

    /* function minus item in cart using jquery ajax  */
    $('.shopping-cart .qty .button.plus button').on("click", function () {
        let buttonPlus = $(this);
        let quantity = buttonPlus.parent().siblings('input').val();
        let unitPrice = buttonPlus.closest('tr').find('td.price span').text();
        let totalAmountItem = parseInt(quantity) * parseInt(unitPrice);
        let data = {
            productUrl : buttonPlus.closest('tr').attr('data-productUrl'),
            changeMethod : "plus"
        }
        $.when(
            $.ajax({
                url: "/api/cart/edit",
                type: "PUT",
                data: JSON.stringify(data),
                dataType: 'json',
                contentType: 'application/json',
                success: function (result) {
                    if (result === false) {
                        alert("không đủ số lượng cung cấp");
                    } else {
                        buttonPlus.closest('tr').find('td.total-amount span').text(totalAmountItem);
                    }
                }
            })
        ).then(function () {
            loadHeaderCart();
            updatePriceCart()
        });
    });
    /* end function minus item in cart */

    /* function remove item cart using jquery ajax */
    $('td.action a').on('click', function () {
        let url = "/api/cart/remove/" + $(this).first().attr('id');
        $.ajax({
            url: url,
            type: "DELETE",
            dataType: 'json',
            success: function (result) {
            }
        }).then(function () {
            loadHeaderCart();
            updatePriceCart()
        });
        $(this).closest('tr').remove();
    })
    /* end function remove item cart */

    /* function remove item cart using jquery ajax */
    $('.shopping-list').on('click', '.remove', function () {
        let url = "/api/cart/remove/" + $(this).parent('li').attr('id');
        $.ajax({
            url: url,
            type: "DELETE",
            dataType: 'json',
            success: function (result) {
            }
        }).then(function () {
            loadHeaderCart();
        });
    })
    /* end function remove item cart */

    /* function quick view product */
    $(".product-action a[title='Quick View']").on("click", function (e) {
        e.preventDefault();
        $.ajax({
            url: $(this).attr('href'),
            type: "GET",
            dataType: 'json',
            success: function (product) {
                $('.quickView-content h2').text(product["productName"]);
                $('.quickView-ratting').empty();
                for (let i = 0; i < product["ratting"]; i++) {
                    $('.quickView-ratting').append("<i class=\"yellow fa fa-star\"></i>");
                }
                for (let i = 0; i < 5 - product["ratting"]; i++) {
                    $('.quickView-ratting').append("<i class=\"fa fa-star\"></i>");
                }
                $('#num-customer-review').text(product["numberOfReviews"]);
                $('.quickView-content h3').text(product["finalPrice"]);
                $('.quickView-peragraph p').text(product["description"]);
                $('#add-cart').attr('data-productUrl', product["productUrl"]);
                if (product["amount"] > 0) {
                    $('#in-stock').css('display', 'block');
                    $('#out-stock').css('display', 'none');
                } else {
                    $('#in-stock').css('display', 'none');
                    $('#out-stock').css('display', 'block');
                }
                $('#productModal').modal('show');
            }
        })
    });
    /* end function quick view product */

    /* function add product to wishlist */
    $(".product-action a[title='Wishlist']").on("click", function (e) {
        e.preventDefault();
        let url = $(this).attr("href");
        removeProductFromWishlist(url);
    });

    $(".add-to-cart a[title='Wishlist']").on("click", function (e) {
        e.preventDefault();
        let url = $(this).attr("href");
        removeProductFromWishlist(url);
    });
    /* function end product to wishlist */
});

function updatePriceCart() {
    $.ajax({
        url: "/api/cart/total-money-cart",
        type: "GET",
        dataType: 'json',
        success: function (data) {
            $('#sub-cart').text(data);
        }
    });
}

function loadHeaderCart() {
    $.ajax({
        url: "/api/cart/load-info-cart",
        type: "GET",
        dataType: 'json',
        success: function (data2) {
            if (data2["numberOfProducts"] === 0) {
                $('.shopping-item').children().css('display', 'none');
                $('#cart-empty-header').css('display', 'block');
            } else {
                $('.shopping-item').children().css('display', 'block');
                $('#cart-empty-header').css('display', 'none');
                $('ul.shopping-list li#item-for-cloning').nextAll().remove();
                let firstRow = $('#item-for-cloning');
                $.each(data2["listItemCart"], function (index, item) {
                    let cloneRow = firstRow.clone();
                    cloneRow.attr('id', item.product["productUrl"]);
                    cloneRow.css('display', 'block');
                    cloneRow.find('a.cart-img').attr('href', '/products/' + item.product["productUrl"]);
                    cloneRow.find('a.cart-item-name').text(item.product["productName"]).attr('href', '/products/' + item.product["productUrl"]);
                    cloneRow.find('span.quantity').text(item.quantity);
                    cloneRow.find('span.amount').text(item.product["finalPrice"]);
                    cloneRow.insertAfter('ul.shopping-list li:last');
                })
                $('span.total-amount').text(data2["totalPriceCart"]);
                $('#total-items').text(data2["numberOfProducts"]);
            }
            $('.total-count').text(data2["numberOfProducts"]);
        }
    });
}

function onloadPage(sortBy, sort, pageSize, minPrice, maxPrice) {
    let infoSort = sortBy + "-" + sort;
    $('#page-size option').each(function () {
        if ($(this).val() === pageSize) {
            $('#page-size').siblings(".nice-select").find('span.current').text($(this).text())
            $(this).attr('selected', "selected");
        }
    });
    $('#page-size').siblings(".nice-select").find('ul.list li').removeClass('selected');
    $('#page-size').siblings(".nice-select").find('ul.list li').each(function () {
        if ($(this).attr('data-value') === pageSize) {
            $(this).addClass('selected');
        }
    });

    $('#sort-by option').each(function () {
        if ($(this).val() === infoSort) {
            $('#sort-by').siblings(".nice-select").find('span.current').text($(this).text())
            $(this).attr('selected', "selected");
        }
    });
    $('#sort-by').siblings(".nice-select").find('ul.list li').removeClass('selected');
    $('#sort-by').siblings(".nice-select").find('ul.list li').each(function () {
        if ($(this).attr('data-value') === pageSize) {
            $(this).addClass('selected');
        }
    });

    $('input[name="min-price"]').val(minPrice);
    $('input[name="max-price"]').val(maxPrice);
}

function filterByPrice(categoryUrl, supplierName) {
    let minPrice = $('input[name="min-price"]').val();
    let maxPrice = $('input[name="max-price"]').val();
    let url = "/collections.html?minPrice=" + minPrice + "&maxPrice=" + maxPrice;
    if (categoryUrl != null) {
        url += "&category=" + categoryUrl;
    }
    if (supplierName != null) {
        url += "&supplier=" + supplierName;
    }
    window.location = url;
}

function getSortAndShow(filterByPrice, categoryUrl, supplierName, keyword) {
    let sort = $("#sort-by option:selected").val().split("-");
    let pageSize = $("#page-size option:selected").val();
    let minPrice = $('input[name="min-price"]').val();
    let maxPrice = $('input[name="max-price"]').val();
    let url;
    if (keyword != null) {
        url = "/search.html?search=" + keyword + "&page=1&sortBy=" + sort[0] + "&sort=" + sort[1] + "&pageSize=" + pageSize;
    } else if (categoryUrl != null && supplierName == null){
        if (filterByPrice === true) {
            url = "/collections.html?category=" + categoryUrl + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice + "&page=1&sortBy=" + sort[0] + "&sort=" + sort[1] + "&pageSize=" + pageSize;
        } else {
            url = "/collections.html?category=" + categoryUrl + "&page=1&sortBy=" + sort[0] + "&sort=" + sort[1] + "&pageSize=" + pageSize;
        }
    } else if (categoryUrl == null && supplierName != null) {
        if (filterByPrice === true) {
            url = "/collections.html?supplier=" + supplierName + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice + "&page=1&sortBy=" + sort[0] + "&sort=" + sort[1] + "&pageSize=" + pageSize;
        } else {
            url = "/collections.html?supplier=" + supplierName + "&page=1&sortBy=" + sort[0] + "&sort=" + sort[1] + "&pageSize=" + pageSize;
        }
    } else if (categoryUrl != null && supplierName != null) {
        if (filterByPrice === true) {
            url = "/collections.html?category=" + categoryUrl + "&supplier=" + supplierName + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice + "&page=1&sortBy=" + sort[0] + "&sort=" + sort[1] + "&pageSize=" + pageSize;
        } else {
            url = "/collections.html?category=" + categoryUrl + "&supplier=" + supplierName + "&page=1&sortBy=" + sort[0] + "&sort=" + sort[1] + "&pageSize=" + pageSize;
        }
    }
    window.location = url;
}

function searchProduct() {
    let keyword = $('input[id="input-search-bar"]').val();
    window.location = "/search.html?search=" + keyword;
}

function removeProductFromWishlist(url) {
    $.ajax({
        url: url,
        type: "POST",
        dataType: 'json',
        success: function (result) {
            if (result === true) {
                alert("Đã thêm vào danh sách yêu thích");
            }
        },
        error: function (err) {
            alert("có lỗi xảy ra");
        }
    });
}
