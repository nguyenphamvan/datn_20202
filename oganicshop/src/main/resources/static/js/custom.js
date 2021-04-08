$(document).ready(function () {

    loadHeaderCart();

    /* function add item in cart using jquery ajax  */
    $('#add-cart').on('click', function () {
        $.when(
            $.ajax({
                url: "/api/cart/add",
                type: "POST",
                data: JSON.stringify({
                    "productUrl": $(this).attr("productUrl"),
                    "quantity": $('input[class="input-number"]').val()
                }),
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
    $(document).on('click', '.shopping-cart .qty .button.minus button', function () {
        let buttonMinus = $(this);
        let quantity = buttonMinus.parent().siblings('input').val();
        let unitPrice = buttonMinus.closest('tr').find('td.price span').text();
        let totalAmountItem = (parseInt(quantity) - parseInt(1)) * parseInt(unitPrice);
        let data = {
            url : buttonMinus.closest('tr').attr('data-url'),
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
                            buttonMinus.parent().siblings('input').val(parseInt(quantity) - parseInt(1));
                            buttonMinus.closest('tr').find('td.total-amount span').text(totalAmountItem);
                        }
                    }
                })
            ).then(function () {
                loadHeaderCart();
                updatePriceCart();
            });
        }
    });
    /* end function minus item in cart */

    /* function minus item in cart using jquery ajax  */
    $(document).on('click', '.shopping-cart .qty .button.plus button' , function () {
        let buttonPlus = $(this);
        let quantity = buttonPlus.parent().siblings('input').val();
        let unitPrice = buttonPlus.closest('tr').find('td.price span').text();
        let totalAmountItem = (parseInt(quantity) + parseInt(1))* parseInt(unitPrice);
        let data = {
            url : buttonPlus.closest('tr').attr('data-url'),
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
                        buttonPlus.parent().siblings('input').val(parseInt(quantity) + parseInt(1));
                        buttonPlus.closest('tr').find('td.total-amount span').text(totalAmountItem);
                    }
                }
            })
        ).then(function () {
            loadHeaderCart();
            updatePriceCart();
        });
    });
    /* end function minus item in cart */

    /* function remove item cart using jquery ajax */
    $(document).on('click', 'td.action a', function () {
        let url = "/api/cart/remove/" + $(this).first().attr('id');
        $.ajax({
            url: url,
            type: "DELETE",
            dataType: 'json',
            success: function (result) {
                if ($("body > div.shopping-cart.section > div > div:nth-child(1) > div > table > tbody > tr").length === 1) {
                    let html = "<div class=\"row\">\n" +
                        "                <div class=\"cart-empty\" style=\"text-align: center; margin: auto;\">\n" +
                        "                    Không có sản phẩm nào trong giỏ hàng\n" +
                        "                </div>\n" +
                        "            </div>";
                    $("body > div.shopping-cart.section > div.container").empty();
                    $("body > div.shopping-cart.section > div.container").append(html);
                }
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
            success: function (data) {
                let product = data["product"];
                // alert(JSON.stringify(product));
                $("#productModal > div > div > div.modal-body > div > div:nth-child(2) > div").attr("product-url", product["productUrl"]);
                $('.quickview-content h2').text(product["productName"]);
                $('.quickview-ratting').empty();
                for (let i = 0; i < product["rating"]; i++) {
                    $('.quickview-ratting').append("<i class=\"yellow fa fa-star\"></i>");
                }
                for (let i = 0; i < 5 - product["rating"]; i++) {
                    $('.quickview-ratting').append("<i class=\"fa fa-star\"></i>");
                }
                $('#num-customer-review').text(product["numberOfReviews"]);
                $('.quickview-content h3').text(product["finalPrice"]);
                $('.quickview-peragraph p').text(product["baseDescription"]);
                $('#add-cart').attr('productUrl', product["productUrl"]);
                $("div.add-to-cart > a.btn.min").attr("href", "/api/account/wishlist/add/" + product["id"]);
                $('.add-to-cart').attr('productUrl', product["productUrl"]);
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
        addProductFromWishlist(url);
    });

    $("div.add-to-cart > a.btn.min").on("click", function (e) {
         e.preventDefault();
         let url = $(this).attr("href");
         alert(url);
        addProductFromWishlist(url);
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
                    cloneRow.attr('id', item.product["url"]);
                    cloneRow.css('display', 'block');
                    cloneRow.find('a.cart-img').attr('href', '/products/' + item.product["url"]);
                    cloneRow.find('a.cart-item-name').text(item.product["name"]).attr('href', '/products/' + item.product["url"]);
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

function addProductFromWishlist(url) {
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
