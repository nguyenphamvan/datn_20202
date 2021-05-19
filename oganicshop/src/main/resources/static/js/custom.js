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
                        $("#myModal").css("display", "block");
                        $("#myModal > div.modal-content > p").text("Sản phảm đã được thêm vào giỏ hàng!!");
                        $("#myModal").delay(3000).fadeOut();
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
        let unitPrice = buttonMinus.closest('tr').find('td.price span').text().replace("$","");
        let totalAmountItem = (parseInt(quantity) - parseInt(1)) * parseInt(unitPrice);
        let data = {
            url: buttonMinus.closest('tr').attr('data-url'),
            changeMethod: "minus"
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
                            buttonMinus.closest('tr').find('td.total-amount span').text("$" + totalAmountItem);
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

    /* function edit item in cart using jquery ajax  */
    $(document).on('click', '.shopping-cart .qty .button.plus button', function () {
        let buttonPlus = $(this);
        let quantity = buttonPlus.parent().siblings('input').val();
        let unitPrice = buttonPlus.closest('tr').find('td.price span').text().replace("$","");
        let totalAmountItem = (parseInt(quantity) + parseInt(1)) * parseInt(unitPrice);
        let data = {
            url: buttonPlus.closest('tr').attr('data-url'),
            changeMethod: "plus"
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
                        $("#myModal").css("display", "block");
                        $("#myModal > div.modal-content > p").text("không đủ số lượng cung cấp!!").css("color", "red");
                    } else {
                        buttonPlus.parent().siblings('input').val(parseInt(quantity) + parseInt(1));
                        buttonPlus.closest('tr').find('td.total-amount span').text("$" + totalAmountItem);
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
            success: function (data) {
                alert("đã xóa sản phẩm khỏi giỏ hàng");
                console.log(data);
                if (data["cart"].length > 0) {
                    if (data["status"] === true) {
                        let html = "<div class=\"row\">\n" +
                            "                <div class=\"cart-empty\" style=\"text-align: center; margin: auto;\">\n" +
                            "                    Không có sản phẩm nào trong giỏ hàng\n" +
                            "                    <div class=\"center\" style='margin-top: 20px;'>\n" +
                            "                         <div class=\"button5\">\n" +
                            "                               <a href=\"/\" style='color: white;' class=\"btn\">Tiếp tục mua sắm</a>\n" +
                            "                          </div>\n" +
                            "                    </div>\n" +
                            "                </div>\n" +
                            "            </div>";
                        $("body > div.shopping-cart.section > div.container").empty();
                        $("body > div.shopping-cart.section > div.container").append(html);
                    }
                }
            }
        }).then(function () {
            loadHeaderCart();
        });
    })
    /* end function remove item cart */


    /* function add product to wishlist */
    $(document).on("click", "div.product-action >  a[title='Wishlist']", function (e) {
        e.preventDefault();
        addProductFromWishlist($(this).attr("href"));
    });

    $(document).on("click", "div.add-to-cart > a.btn.min", function (e) {
        e.preventDefault();
        addProductFromWishlist($(this).attr("href"));
    });
    /* function end product to wishlist */
});

function updatePriceCart() {
    $.ajax({
        url: "/api/cart/total-money-cart",
        type: "GET",
        dataType: 'json',
        success: function (data) {
            $('#sub-cart').text("$" + data);
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
                    cloneRow.find('span.amount').text("$" + item.product["finalPrice"]);
                    cloneRow.insertAfter('ul.shopping-list li:last');
                })
                $('span.total-amount').text("$" + data2["totalPriceCart"]);
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
    } else if (categoryUrl != null && supplierName == null) {
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
    window.location.href = "/search.html?search=" + keyword;
}

function addProductFromWishlist(url) {
    $.ajax({
        url: url,
        type: "POST",
        dataType: 'json',
        success: function (result) {
            if (result === true) {
                $("#myModal").css("display", "block");
                $("#myModal > div.modal-content > p").text("Đã thêm vào danh sách yêu thích!!");
                $("#myModal").delay(3000).fadeOut();
            }
        },
        error: function (err) {
            alert("có lỗi xảy ra");
        }
    });
}

function getListCategory() {
    $.ajax({
        url: "/api/categories",
        type: "GET",
        contentType: "application/json",
        success: function (categories) {
            // show category
            let html = "";
            $.each(categories, function (index, item) {
                html += "<li>\n" +
                    "            <a class=\"parent-category\" href='/collections.html?category=" + item["categoryUrl"] + "'>" + item["categoryName"] + "</a>\n" +
                    "            <ul class=\"list-child-category\">\n";
                if (item.hasOwnProperty("subCategory")) {
                    $.each(item["subCategory"], function (index, subItem) {
                        html += "<li>\n" +
                            "         <a href='/collections.html?category=" + subItem["categoryUrl"] + "'>" + subItem["categoryName"] + "</a>\n" +
                            "    </li>\n";
                    });
                }
                html += "     </ul>\n" +
                    "</li>";
            });
            $("div.header-inner > div > div > div > div > div > nav > div > div > ul > li:nth-child(2) > ul").append(html);

        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });
}

function actionForModal() {

    // Get the modal
    var modal = document.getElementById("myModal");

    // Get the <span> element that closes the modal
    let span = $(".closeModal")[0];

    // When the user clicks on <span> (x), close the modal
    span.onclick = function () {
        $("#myModal").hide();
    }

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function (event) {
        if (event.target == modal) {
            $("#myModal").hide();
        }
    }
}
