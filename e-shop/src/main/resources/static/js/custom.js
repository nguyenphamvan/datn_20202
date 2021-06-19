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
                success: function (res) {
                    $(".quickview-content .error-msg").remove();
                    if (res["status"] === false) {
                        $(".quickview-content .add-to-cart").after("<div class='error-msg' style='color: red;'>" + res['errMessage'] + "</div>");
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
                        if (result["status"] === false) {
                            $(".quickview-content .add-to-cart").after("<div class='error-msg' style='color: red;'>" + result['errMessage'] + "</div>");
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
            success: function (res) {
                if (res["status"] === true && parseInt(res["data"]) === 0) {
                    $("body > div.shopping-cart.section > div.container").empty();
                    let html = $("<div class=\"row\">\n" +
                        "                <div class=\"cart-empty\" style=\"text-align: center; margin: auto;\">\n" +
                        "                    <img style=\"color: green ; font-size: 50px; display: block; margin: auto; width: fit-content; margin-top: 20px;\" src='/images/cart-empty.png'>" +
                        "                    <div><p style='color: red;'>Không có sản phẩm nào trong giỏ hàng</p></div>\n" +
                        "                    <div class=\"center\" style='margin-top: 20px;'>\n" +
                        "                         <div class=\"button5\">\n" +
                        "                               <a href=\"/\" style='color: white;' class=\"btn\">Tiếp tục mua sắm</a>\n" +
                        "                          </div>\n" +
                        "                    </div>\n" +
                        "                </div>\n" +
                        "            </div>").hide().fadeIn(2000);
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
        let url = "/api/cart/remove/" + $(this).parent('li').attr('url');
        let productUrl = $(this).parent('li').attr('url');
        $.ajax({
            url: url,
            type: "DELETE",
            dataType: 'json',
            success: function (result) {
                if (result["data"] > 0) {
                    $('#payment > div > div.col-lg-7 > div.header > div > div > ul.cart-item-list > li').each(function() {
                        if ($(this).attr("url") === productUrl) {
                            $(this).remove();
                        }
                    });
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

    $(document).on("click", "#add-wishlist", function (e) {
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
    let thisPage = window.location.pathname;
    $.ajax({
        url: "/api/cart/load-info-cart",
        type: "GET",
        dataType: 'json',
        success: function (res) {
            if (res["data"] === null) {
                $('div.sinlge-bar.shopping > div.shopping-item').children().css('display', 'none');
                $('#cart-empty-header').css('display', 'block');
                $('.total-count').text("0");
                if (thisPage === "/checkout.html") {
                    window.location = "/cart.html";
                }
            } else {
                $('div.sinlge-bar.shopping > div.shopping-item').children().css('display', 'block');
                $('#cart-empty-header').css('display', 'none');
                $('ul.shopping-list li#item-for-cloning').nextAll().remove();
                let firstRow = $('#item-for-cloning');
                $.each(res["data"]["listItemCart"], function (index, item) {
                    let cloneRow = firstRow.clone();
                    cloneRow.attr('url', item.product["productUrl"]);
                    cloneRow.css('display', 'block');
                    cloneRow.find('a.cart-img').attr('href', item.product["productUrl"]);
                    cloneRow.find('a.cart-img > img').attr('src', item.product["mainImage"]);
                    cloneRow.find('a.cart-item-name').text(item.product["name"]).attr('href', '/products/' + item.product["productUrl"]);
                    cloneRow.find('span.quantity').text(item.quantity);
                    cloneRow.find('span.amount').text("$" + item.product["finalPrice"]);
                    cloneRow.insertAfter('ul.shopping-list li:last');
                })
                $('span.total-amount').text("$" + res["data"]["totalPriceCart"]);
                $('#total-items').text(res["data"]["numberOfProducts"]);
                $('.total-count').text(res["data"]["numberOfProducts"]);
            }

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

function searchProduct() {
    let keyword = $('input[id="input-search-bar"]').val();
    window.location.href = "/search?keyword=" + keyword;
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
                    "        <a class=\"parent-category\" href='/collections.html?category=" + item["id"] + "'>" + item["categoryName"] + "</a>\n" +
                    "    </li>";
            });
            $("div.header-inner > div > div > div > div > div > nav > div > div > ul > li:nth-child(2) > ul").append(html);

        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });
}

function getListCategory2() {
    $.ajax({
        url: "/api/categories",
        type: "GET",
        contentType: "application/json",
        success: function (categories) {
            // show category
            let html = "";
            $.each(categories, function (index, item) {
                html += "<li style='margin-bottom: 10px;'>\n" +
                    "        <a href='/collections.html?category=" + item["id"] + "'>" + item["categoryName"] + "</a>\n" +
                    "    </li>";
            });
            $("ul.category-list").append(html);
        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });
}

function getListCategoryHome() {
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
                    "            <ul class=\"sub-category\">\n";
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
            $("body > header > div.header-inner > div > div > div > div.col-lg-3 > div > ul").append(html);

        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });
}

function genListProduct(products) {
    let firstItem = $("#list-product > div:first-child");
    $.each(products, function (index, item) {
        let itemClone = firstItem.clone();
        itemClone.show();
        if (item.hasOwnProperty("mainImage")) {
            itemClone.find("div.product-img > a").attr("href", "/products/" + item["productUrl"] + ".html");
            itemClone.find("div.product-img > a > img.default-img").attr("src", item["mainImage"]);
            itemClone.find("div.product-img > a > img.hover-img").attr("src", item["mainImage"]);
        }
        itemClone.find("div.product-content > h3 > a").attr("href", "/products/" + item["productUrl"] + ".html").text(item["productName"]);
        itemClone.find("div.product-action-2 > a").attr("href", "/products/" + item["productUrl"] + ".html");
        itemClone.find("div.product-content > div > span:nth-child(1)").text("$" + item["finalPrice"]);
        itemClone.find("div.product-content > div > span.old").text("$" + item["price"]);
        $("#list-product").append(itemClone);
    });
    firstItem.hide();
}

function getListProductByKeyword(data) {
    $.ajax({
        url: "/api/search",
        type: "POST",
        data: JSON.stringify(data),
        dataType: "json",
        contentType: "application/json",
        success: function (result) {
            console.log(result);
            if (result["products"].length > 0) {
                genListProduct(result["products"]);

                $("section.product-area.shop-sidebar.shop.section > div > div > div.col-lg-9.col-md-8.col-12 > div.pagination-nav.text-center.mt_50 > ul")
                    .empty();
                let html = "<li page='1'><a>First</a></li>\n";
                if (result["page"] > 1) {
                    html += "<li page=" + (parseInt(result["page"]) - 1) + "><a ><i class=\"fa fa-angle-left\"></i></a></li>\n";
                } else {
                    html += "<li page='1'><a ><i class=\"fa fa-angle-left\"></i></a></li>\n";
                }
                for (let i = 1; i <= result["totalPages"]; ++i) {
                    if (i === result["page"]) {
                        html += "<li page=" + i + " class='active'><a>" + i + "</a></li>";
                    } else {
                        html += "<li page=" + i + "><a>" + i + "</a></li>";
                    }
                }
                if (parseInt(result["page"]) < parseInt(result["totalPages"])) {
                    html += "<li page=" + (parseInt(result["page"]) + 1) + "><a ><i class=\"fa fa-angle-right\"></i></a></li>\n";
                } else {
                    html += "<li page=" + parseInt(result["page"]) + "><a ><i class=\"fa fa-angle-right\"></i></a></li>\n";
                }
                html += "<li page=" + parseInt(result["totalPages"]) + "><a>Last</a></li>";
                $("div.pagination-nav.text-center.mt_50 > ul")
                    .append(html);
            } else {
                $("body > section > div > div").empty().text("Không tìm thấy sản phẩm nào cho từ khóa " + result["keyword"]);
            }
        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });

}

function getInfoAccount() {
    $.ajax({
        url: "/api/account/info",
        type: "GET",
        dataType: 'json',
        success: function (data) {
            if (data["status"] === true) {
                let info = data["userInfo"];
                $("input[name='fullName']").val(info["fullName"]);
                $("input[name='phoneNumber']").val(info["phone"]);
                $("input[name='email']").val(info["email"]);
                $("input[name='birthday']").val(info["birthday"]);
                let $radios = $('input:radio[name=gender]');
                if (info["gender"] === "male") {
                    $radios.filter('[value=male]').prop('checked', true);
                } else {
                    $radios.filter('[value=female]').prop('checked', true);
                }
            }
        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });
}

function updateInfoAccount(data) {
    $.ajax({
        url: "/api/account/update",
        data: JSON.stringify(data),
        type: "PUT",
        dataType: 'json',
        contentType: "application/json",
        cache: false,
        success: function (data) {
            if (data["status"] === true) {
                $("#myModal").fadeIn();
                $("#myModal > div.modal-content > p").text("Cập nhật thông tin thành công!");
                $("#myModal").delay(3000).fadeOut();

                // user info
                $("input[name='fullName']").val(data["userInfo"]["fullName"]);
                $("input[name='phoneNumber']").val(data["userInfo"]["phone"]);
                $("input[name='email']").val(data["userInfo"]["email"]);
                $("input[name='birthday']").val(data["userInfo"]["birthday"]);
                let $radios = $('input:radio[name=gender]');
                if (data["userInfo"]["gender"] === "male") {
                    $radios.filter('[value=male]').prop('checked', true);
                } else {
                    $radios.filter('[value=female]').prop('checked', true);
                }
            }
        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });
}

function validatePassword() {
    let oldPassword = $("input[name='oldPassword']").val();
    let password = $("input[name='password']").val();
    let passwordConfirm = $("input[name='passwordConfirm']").val();
    let token = true;
    if (oldPassword.length === 0) {
        token = false;
        $("input[name='oldPassword']").closest("div.div-form-control").addClass("has-error");
        $("<div class=\"error-message\">Vui lòng nhập mật khẩu cũ</div>").insertAfter($("input[name='oldPassword']"));
    }

    if (password.length === 0) {
        token = false;
        $("input[name='password']").closest("div.div-form-control").addClass("has-error");
        $("<div class=\"error-message\">Vui lòng nhập mật khẩu mới</div>").insertAfter($("input[name='password']"));
    }

    if (passwordConfirm.length === 0) {
        token = false;
        $("input[name='passwordConfirm']").closest("div.div-form-control").addClass("has-error");
        $("<div class=\"error-message\">Bạn chưa xác nhận mật khẩu mới</div>").insertAfter($("input[name='passwordConfirm']"));
    }

    if (oldPassword.length > 0 && password.length > 0 && passwordConfirm.length > 0) {
        $.ajax({
            url: "/api/account/check-password?oldPassword=" + oldPassword,
            type: "GET",
            success: function (isMatch) {
                if (isMatch === false) {
                    token = false;
                    $("input[name='oldPassword']").closest("div.div-form-control").addClass("has-error");
                    $("<div class=\"error-message\">Mật khẩu cũ không đúng</div>").insertAfter($("input[name='oldPassword']"));
                }
                if (password !== passwordConfirm) {
                    token = false;
                    $("input[name='passwordConfirm']").closest("div.div-form-control").addClass("has-error");
                    $("<div class=\"error-message\">Mật khẩu không trùng khớp</div>").insertAfter($("input[name='passwordConfirm']"));
                }
            },
            error: function (xhr) {
                alert(xhr.responseText);
            }
        });
    }
    return token;
}

function getProductsIsUnReviewed() {
    $.ajax({
        url: "/api/order/product-not-reviewed",
        type: "GET",
        dataType: 'json',
        success: function (data) {
            let listItem = data["products"]
            $(".my-reviews__inner a:first-child").show().nextAll().remove();
            if (listItem.length > 0) {
                let firstItem = $(".my-reviews__inner a:first-child");
                $.each(listItem, function (index, item) {
                    let itemClone = firstItem.clone();
                    itemClone.attr("href", "/products/" + item["productUrl"]);
                    itemClone.find("div.my-reviews__info").attr("product-id", item["productId"]).attr("orderItem-id", item["id"]);
                    itemClone.find("div.my-reviews__info > img").attr("src", item["image"]).attr("alt", item["productName"]);
                    itemClone.find("div.my-reviews__name").text(item["productName"]);
                    itemClone.insertAfter(".my-reviews__inner a:first-child");
                });
                firstItem.hide();
            } else {
                $(".style__StyledMyReviews").empty()
                    .append("<div class=\"col-12\">\n" +
                        "                <div style=\"text-align: center; margin: auto; margin-top: 50px;\">\n" +
                        "                    <p style=\"font-size: 20px; color: green;\">Không có sản phẩm nào chưa nhận xét!</p>\n" +
                        "                    <div class=\"center\" style=\"margin-top: 20px;\">\n" +
                        "                        <div class=\"button5\">\n" +
                        "                            <a href='/' style=\"color: white;\" class=\"btn\">Tiếp tục mua sắm</a>\n" +
                        "                        </div>\n" +
                        "                    </div>\n" +
                        "                </div>\n" +
                        "     </div>");
            }
        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });
}

function postReview(data) {
    $.ajax({
        url: "/api/review/post",
        data: JSON.stringify(data),
        type: "POST",
        contentType: "application/json",
        success: function (result) {
            $("#myModal").css("display", "block");
            $("#myModal > div.modal-content > p").text(result);
            $("#myModal").delay(1000).fadeOut();
            getProductsIsUnReviewed();
        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });
}

function getListMyReview(currentPage, pageSize) {
    $.ajax({
        url: "/api/review/my-review?currentPage=" + currentPage + "&pageSize=" + pageSize,
        type: "GET",
        dataType: 'json',
        success: function (data) {
            $(".styles__StyledReviewList .heading span").text(data["numberOfReview"]);
            $(".Pagination__StyledPagination").empty();
            let listMyReview = data["reviews"];
            if (listMyReview.length > 0) {
                let firstItem = $(".styles__StyledReviewList div.list .item:first-child");
                $(".styles__StyledReviewList div.list .item:first-child").nextAll().remove();
                $.each(listMyReview, function (index, item) {
                    let itemClone = firstItem.clone();
                    itemClone.find("div.thumb > div > img").attr("src", item["productImg"]);
                    itemClone.find(".info a.name").attr("href", "/products/" + item["productUrl"]).text(item["productName"]);
                    itemClone.find(".info div.date").text(item["createdAt"]);
                    if (item["title"] !== null) {
                        itemClone.find(".info .wrap .title").text(item["title"]);
                    }
                    if (parseInt(item["rating"]) > 0) {
                        for (let i = 0; i < parseInt(item["rating"]); i++) {
                            itemClone.find(".ratting-star").append("<i class=\"yellow fa fa-star\"></i>");
                        }
                        for (let i = 0; i < (5 - parseInt(item["rating"])); i++) {
                            itemClone.find(".ratting-star").append("<i class=\"fa fa-star\"></i>");
                        }
                    }
                    itemClone.find(".info .content-review").text(item["comment"]);
                    itemClone.show();
                    itemClone.insertAfter(".styles__StyledReviewList div.list .item:first-child");

                });

                for (let i = 0; i < parseInt(data["totalPage"]); ++i) {
                    if ( (i + 1) === parseInt(data["currentPage"]) ) {
                        $(".Pagination__StyledPagination").append("<li class='is-active'>" + (i + 1) + "</li>");
                    } else {
                        $(".Pagination__StyledPagination").append("<li>" + (i + 1) + "</li>")
                    }
                }
            } else {
                $(".styles__StyledReviewList").empty()
                    .append("<div style='text-align: center; padding: 50px;'><a href='/'><button class='btn-info'>Tiếp tục muc sắm</button></a></div>")
            }
        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });
}

function getTheShippingAddress() {
    $.ajax({
        url: "/api/account/address",
        type: "GET",
        dataType: 'json',
        contentType: "application/json",
        success: function (address) {
            let firstDivAddr = $("div.Account__StyledAccountLayoutInner > div.styles__StyledAccountAddress > div.item");
            $.each(address, function( index, item ) {
                let itemClone = firstDivAddr.clone();
                itemClone.show();
                itemClone.attr("isDefault", item["default"]);
                itemClone.attr("id-shippingAddr", item["id"]);
                itemClone.find(".name span:first-child").text(item["contactReceiver"]);
                if (item["default"] === false) {
                    itemClone.find(".name span:nth-child(2)").hide();
                    itemClone.find("div.action > a.edit").after("<button class='delete'>Xóa</button>");
                }
                itemClone.find(".address span:nth-child(2)").text(item["contactAddress"]);
                itemClone.find(".phone span:nth-child(2)").text(item["contactPhone"]);
                itemClone.insertAfter(firstDivAddr);
            });
            firstDivAddr.hide();
            $("div.styles__StyledAccountAddress").show();
            $("div.styles__StyledAccountAddressEdit").hide();
            $("div.styles__StyledAccountAddressCreate").hide();
        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });
}

function addShippingAddress(isCreate, data) {
    let url = "";
    let type = "";
    if(isCreate === true) {
        url = "/api/account/address/create";
        type = "POST";
    } else {
        url = "/api/account/address/update";
        type = "PUT";
    }

    $.ajax({
        url: url,
        type: type,
        data: JSON.stringify(data),
        dataType: 'json',
        contentType: "application/json",
        success: function (data) {
            if (data === true) {
                $("#myModal").fadeIn();
                $("#myModal > div.modal-content > img").attr("src", "/images/check-success-icon.png");
                $("#myModal > div.modal-content > p").text("Lưu địa chỉ thành công!");
                $("#myModal").delay(3000).fadeOut();
                $("div.Account__StyledAccountLayoutInner > div.styles__StyledAccountAddress > div.item").nextAll().remove();
                getTheShippingAddress();
            }
        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });
}

function deleteShippingAddress(thisItem, addressId) {
    cuteAlert({
        type: "question",
        title: "Xác nhận hành động",
        message: "Bạn chắc chắn muốn xóa địa chỉ này",
        confirmText: "Xóa",
        cancelText: "Hủy"
    });
    $("button.confirm-button.question-bg.question-btn").on("click", function () {
        $.ajax({
            url: "/api/account/address/delete/" + addressId,
            type: "DELETE",
            contentType: "application/json",
            success: function (data) {
                if (data === true) {
                    $("#myModal").fadeIn();
                    $("#myModal > div.modal-content > img").attr("src", "/images/not-enough.png");
                    $("#myModal > div.modal-content > p").text("Xóa thành công!");
                    $("#myModal").delay(3000).fadeOut();
                    thisItem.closest("div.item").remove();
                }
            },
            error: function (xhr) {
                alert(xhr.responseText);
            }
        });
    });

}

function getWishlistProducts() {
    $.ajax({
        url: "/api/account/wishlist",
        type: "GET",
        dataType: 'json',
        success: function (data) {
            console.log(data)
            if(data.hasOwnProperty("wishLists")) {
                let wishlist = data["wishLists"];
                $(".styles__StyledAccountWishList .heading span").text(wishlist.length);
                if (wishlist.length > 0) {
                    let firstItem = $(".styles__StyledAccountWishList ul.list li:first-child");
                    $.each(wishlist, function (index, item) {
                        let itemClone = firstItem.clone();
                        itemClone.find(".btn-delete").attr("product-id", item["id"]);
                        itemClone.find(".thumbnail a").attr("href", "/products/" + item["productUrl"]);
                        itemClone.find(".thumbnail a img.image").attr("src", item["mainImage"]);
                        itemClone.find(".body a.name").attr("href", "/products/" + item["productUrl"]).text(item["productName"]);
                        itemClone.find(".body .description").text(item["description"].substring(0, 100) + "...");
                        itemClone.find("div.my-reviews__name").text(item["productUrl"]);
                        for (let i = 0; i < parseInt(item["rating"]); i++) {
                            itemClone.find(".ratting-star").append("<i class=\"yellow fa fa-star\"></i>");
                        }
                        for (let i = 0; i < (5 - parseInt(item["rating"])); i++) {
                            itemClone.find(".ratting-star").append("<i class=\"fa fa-star\"></i>");
                        }
                        itemClone.find(".ratting-wrap span").text("(" + item["numberOfReviews"] + " customer review)")
                        itemClone.find(".footer_favourite .final-price").text("$" + item["finalPrice"]);
                        itemClone.find(".footer_favourite .wrap .price").text("$" + item["price"]);
                        if ((parseFloat(item["discount"])*100 ) > 0) {
                            itemClone.find(".footer_favourite .wrap .discount").text("-" + (parseFloat(item["discount"])*100) + "%").attr("color", "red");
                        }
                        itemClone.insertAfter(".styles__StyledAccountWishList ul.list li:first-child");
                    });
                    firstItem.remove();
                } else {
                    $(".styles__StyledAccountWishList").empty()
                        .append("<div class=\"col-12\">\n" +
                            "                <div style=\"text-align: center; margin: auto; margin-top: 50px;\">\n" +
                            "                    <p style=\"font-size: 20px; color: green;\">Không có sản trong danh sách yêu thichd!!</p>\n" +
                            "                    <div class=\"center\" style=\"margin-top: 20px;\">\n" +
                            "                        <div class=\"button5\">\n" +
                            "                            <a href='/' style=\"color: white;\" class=\"btn\">Tiếp tục mua sắm</a>\n" +
                            "                        </div>\n" +
                            "                    </div>\n" +
                            "                </div>\n" +
                            " </div>");
                }
            } else {
                $(".styles__StyledAccountWishList").empty()
                    .append("<div class=\"col-12\">\n" +
                        "                <div style=\"text-align: center; margin: auto; margin-top: 50px;\">\n" +
                        "                    <p style=\"font-size: 20px; color: green;\">Không có sản trong danh sách yêu thichd!!</p>\n" +
                        "                    <div class=\"center\" style=\"margin-top: 20px;\">\n" +
                        "                        <div class=\"button5\">\n" +
                        "                            <a href='/' style=\"color: white;\" class=\"btn\">Tiếp tục mua sắm</a>\n" +
                        "                        </div>\n" +
                        "                    </div>\n" +
                        "                </div>\n" +
                        " </div>");

            }
        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });
}

function removeProductFromWishlist(buttonDelete, productId) {
    cuteAlert({
        type: "question",
        title: "Xác nhận hành động",
        message: "Bạn chắc chắn muốn xóa sản phẩm khỏi wishlist",
        confirmText: "Xóa",
        cancelText: "Hủy"
    });

    $("button.confirm-button.question-bg.question-btn").on("click", function () {
        $.ajax({
            url: "/api/account/wishlist/remove/" + productId,
            type: "DELETE",
            dataType: 'json',
            success: function (result) {
                if (result === true) {
                    $("#myModal").fadeIn();
                    $("#myModal > div.modal-content > p").text("Xóa thành công!");
                    $("#myModal").delay(3000).fadeOut();
                    let heading = $(".styles__StyledAccountWishList .heading span");
                    heading.text(parseInt(heading.text()) - 1);
                    buttonDelete.closest("li.item").remove();
                }
            },
            error: function (xhr) {
                alert(xhr.responseText);
            }
        });
    });

}

function getProductsInCart() {
    $.ajax({
        url: "/api/cart/all",
        type: "GET",
        contentType: "application/json",
        success: function (res) {
            console.log(res);
            if (res["status"] === true) {
                let items = res["data"];
                let totalCart = 0;
                let firstTr = $("body > div.shopping-cart.section > div > div:nth-child(1) > div > table > tbody > tr:first-child");
                $.each(items, function( index, item ) {
                    totalCart = totalCart + parseInt(item["totalItem"]);
                    let trClone = firstTr.clone();
                    trClone.attr("data-url", item["product"]["productUrl"]);
                    trClone.find("td.image > a").attr("href", "/products/" + item["product"]["productUrl"] + ".html");
                    trClone.find("td.image > a > img").attr("src", item["product"]["mainImage"]);
                    trClone.find("td.product-des > p.product-name > a").attr("href", "/products/" + item["product"]["productUrl"] + ".html")
                        .text(item["product"]["productName"]);
                    trClone.find("td.price > span").text("$" + item["product"]["finalPrice"]);
                    trClone.find("td.qty > div > input").val(item["quantity"]);
                    trClone.find("td.total-amount > span").text("$" + item["totalItem"]);
                    trClone.find("td.action > a").attr("id", item["product"]["productUrl"]);
                    trClone.insertAfter(firstTr);
                });
                firstTr.remove();
                $("#sub-cart").text("$" + totalCart);
            } else {
                let html = "<div class=\"row\">\n" +
                    "                <div class=\"cart-empty\" style=\"text-align: center; margin: auto;\">\n" +
                    "                    <img style=\"color: green ; font-size: 50px; display: block; margin: auto; width: fit-content; margin-top: 20px;\" src='/images/cart-empty.png'>" +
                    "                    <div><p style='color: red;'>Không có sản phẩm nào trong giỏ hàng</p></div>\n" +
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
        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });
}

function getDiscountForOrder() {
    $.ajax({
        url: "/api/checkout/getMyDiscount",
        type: "GET",
        contentType: "application/json",
        success: function (data) {

            if (data.length > 0) {
                let firstItem = $("#discountModal > div > div > div.modal-body > div > div:nth-child(1)");
                $.each(data, function (index, item) {
                    let itemClone = firstItem.clone();
                    itemClone.show();
                    itemClone.find("p.title > span:nth-child(1)").text(item["title"]);
                    itemClone.find("p.code > span:nth-child(2)").text(item["code"]);
                    itemClone.find("p.end-date > span:nth-child(2)").text(item["endDate"]);
                    itemClone.insertAfter(firstItem);
                });
                firstItem.hide();
            } else {
                $("#payment > div > div:nth-child(2) > div > div:nth-child(2) > div:nth-child(1) > a").remove();
                $("#discountModal").remove();
            }


        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });
}

function getShippingAddress() {
    $.ajax({
        url: "/api/account/address",
        type: "GET",
        dataType: 'json',
        contentType: "application/json",
        success: function (address) {
            if (address.length > 0) {
                let firstDivAddr = $("#addressModal > div > div > div.modal-body > div > div:nth-child(1)");
                $.each(address, function (index, item) {
                    let itemClone = firstDivAddr.clone();
                    itemClone.show();
                    itemClone.attr("isDefault", item["default"]);
                    itemClone.find("p.name > span:nth-child(1)").text(item["contactReceiver"]);
                    if (item["default"] === true) {
                        itemClone.find("p.name > span:nth-child(1)")
                            .after("<span style=\"margin: 0px 0px 0px 15px; color: rgb(38, 188, 78);\">\n" +
                                "                                        <svg stroke=\"currentColor\" fill=\"currentColor\" stroke-width=\"0\" viewBox=\"0 0 512 512\" height=\"1em\" width=\"1em\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                                "                                            <path d=\"M256 8C119.033 8 8 119.033 8 256s111.033 248 248 248 248-111.033 248-248S392.967 8 256 8zm0 48c110.532 0 200 89.451 200 200 0 110.532-89.451 200-200 200-110.532 0-200-89.451-200-200 0-110.532 89.451-200 200-200m140.204 130.267l-22.536-22.718c-4.667-4.705-12.265-4.736-16.97-.068L215.346 303.697l-59.792-60.277c-4.667-4.705-12.265-4.736-16.97-.069l-22.719 22.536c-4.705 4.667-4.736 12.265-.068 16.971l90.781 91.516c4.667 4.705 12.265 4.736 16.97.068l172.589-171.204c4.704-4.668 4.734-12.266.067-16.971z\"></path>\n" +
                                "                                        </svg>\n" +
                                "                                        <span>Địa chỉ mặc định</span>\n" +
                                "                                    </span>");
                        itemClone.find("p.action > button.saving-address").css("color", "rgb(38, 188, 78);");
                    }
                    itemClone.find("p.address > span:nth-child(2)").text(item["contactAddress"]);
                    itemClone.find("p.phone > span:nth-child(2)").text(item["contactPhone"]);
                    itemClone.css("display", "block");
                    itemClone.insertAfter(firstDivAddr);
                });
                firstDivAddr.hide();
            } else {
                $('#addressModal').remove();
            }
        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });
}

function getInfoOrder(orderId) {
    $.ajax({
        url: "/api/order/view/" + orderId,
        type: "GET",
        success: function (order) {
            if (order !== null) {
                $(".styles__StyledAccountOrderDetail .heading span:first-child").text("Chi tiết đơn hàng #" + order["id"]);
                $(".styles__StyledAccountOrderDetail .heading span.status").text(order["status"]);
                $(".styles__StyledAccountOrderDetail .created-date").text("Ngày đặt hàng: " + order["orderDate"]);
                $(".styles__StyledAccountOrderDetail .message").find(".notifications__item .date").text(order["message"].split(" - ")[0]);
                $(".styles__StyledAccountOrderDetail .message").find(".notifications__item .comment").text(order["message"].split(" - ")[1]);
                $(".styles__StyledSection.order-info-1 > div.content > p.name").text(order["contactReceiver"]);
                $(".styles__StyledSection.order-info-1 .content p.address span:last-child").text(order["address"]["contactAddress"]);
                $(".styles__StyledSection.order-info-1 .content p.phone span:last-child").text(order["address"]["contactPhone"]);
                if (order["status"] === "Đang xử lý") {
                    $("div.Account__StyledAccountLayoutInner > div > table > tfoot")
                        .append("<tr><td colspan=\"4\"></td><td><a title=\"Hủy đơn hàng\" class=\"cancel-order\">Hủy đơn hàng</a></td></tr>");
                }
                $(".styles__StyledSection.order-info-3 .content p:first-child").text(order["paymentMethod"]);

                genreListOrderDetail(order["listOrderDetail"]);
                $("table tfoot tr:first-child td:nth-child(2)").text("$" + order["subTotal"]);
                $("table tfoot tr:nth-child(2) td:nth-child(2)").text("- $" + order["discount"]);
                $("table tfoot tr:nth-child(3) td:nth-child(2)").text("$" + order["shipFee"]);
                $("table tfoot tr:nth-child(4) td:nth-child(2) span.sum").text("$" + order["total"]);
            } else {
                $(".styles__StyledAccountOrderDetail").empty();
                $(".styles__StyledAccountOrderDetail").append("<div style='margin: auto;'>Không tìm thấy đơn hàng</div>")
            }
        },
        error: function (xhr) {
            alert("Lỗi");
            alert(xhr.responseText);
        }
    });
}

function genreListOrderDetail(listOrderDetail) {
    console.log(listOrderDetail);
    let firstRow = $("div.Account__StyledAccountLayoutInner > div > table > tbody > tr:first-child");
    $.each(listOrderDetail, function( index, orderItem ) {
        let rowClone = firstRow.clone();
        rowClone.find("td:nth-child(1) > div").attr("orderItem-id", orderItem["id"]).attr("product-id", orderItem["productId"]);
        rowClone.find("td:nth-child(1) > div > img").attr("src", orderItem["image"]);
        rowClone.find("td:nth-child(1) > div > div > a.product-name").attr("href", "/products/" + orderItem["productUrl"]).text(orderItem["productName"]);
        rowClone.find("td:nth-child(1) > div > div > div.product-review > a").attr("href", "/products/" + orderItem["productUrl"]);
        rowClone.find("td.price").text("$" + orderItem["price"]);
        rowClone.find("td.quantity").text(orderItem["quantity"]);
        rowClone.find("td.promotion-amount").text("$" + orderItem["discount"]);
        rowClone.find("td.raw-total").text("$" + orderItem["rawTotal"]);
        rowClone.insertAfter(firstRow);
    });
    firstRow.remove();
}

function getTotalOrderPage() {
    let data = {
        "pageSize": 5
    };
    $.ajax({
        url: "/api/order/paging",
        type: "GET",
        data: data,
        dataType: 'json',
        contentType: "application/json",
        success: function (totalPage) {
            for (let i = 0; i < parseInt(totalPage); ++i) {
                $(".Pagination__StyledPagination").append("<li>" + (i + 1) + "</li>");
            }
            $(".Pagination__StyledPagination > li:first-child").addClass("is-active");
        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });
}

function getOrdersHistory(pageNum, pageSize) {
    $.ajax({
        url: "/api/order/history",
        type: "GET",
        data: {
            "pageNum": pageNum,
            "pageSize": pageSize
        },
        contentType: "application/json",
        success: function (orders) {
            if (orders.length > 0) {
                $.each(orders, function (index, value) {
                    let node = "<tr>\n" +
                        "           <td><a style='color: rgb(0, 127, 240)' href='/sales/order/view/" + value["id"] + "'>" + value["id"] + "</a></td>\n" +
                        "           <td>" + value["orderDate"] + "</td>\n" +
                        "           <td>" + value["summaryProductName"] + "</td>\n" +
                        "           <td>" + value["total"] + " ₫</td>\n";
                    if (value["status"] === "Giao hàng thành công") {
                        node += "<td style='text-align: center;'><span class='badge badge-pill badge-success'>Giao hàng thành công</span></td>\n";
                    } else if (value["status"] === "Đang xử lý") {
                        node += "<td style='text-align: center;'><span class='badge badge-pill badge-warning'>Đang xử lý</span></td>\n";
                    } else if (value["status"] === "Đã hủy") {
                        node += "<td style='text-align: center;'><span class='badge badge-pill badge-danger'>Đã hủy</span></td>\n";
                    } else {
                        node += "<td style='text-align: center;'><span class='badge badge-pill badge-dark'>" + value["status"] + "</span></td>\n";
                    }
                    node +=    "       </tr>";

                    $('tbody').append(node);
                });
            } else {
                $("section.product-area.shop-sidebar.shop.section > div > div > div.Account__StyledAccountLayoutInner").empty();
                $("section.product-area.shop-sidebar.shop.section > div > div > div.Account__StyledAccountLayoutInner")
                    .append("<div style='text-align: center; padding-top: 50px;'>" +
                        "<a href='/' class='btn-info'>Tiếp tục mua hàng</a> " +
                        "</div>")
            }
        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });
}

function getOrderLogging(orderId) {
    $.ajax({
        url: "/api/order/tracking/" + orderId,
        type: "GET",
        dataType: 'json',
        success: function (order) {
            let orderStatus = order["order"]["orderStatus"];
            $(".order-heading h3").text("Theo dõi đơn hàng #" + orderStatus["orderId"]);
            $(".status-block .status-text b").text(orderStatus["latestStatus"] + "  |  " + orderStatus["lastUpdatedTime"]);
            $(".cGrxj").text("Cập nhật mới nhất : " + orderStatus["lastUpdatedTime"]);

            if (order.hasOwnProperty("loggingStatus")) {
                $.each(orderStatus["loggingStatus"], function( index, orderStatus ) {
                    let html = "<div class=\"styles__StyledOrderStatusDetailItem dPnPGf\">\n" +
                        "           <span class=\"left\">" + orderStatus["updatedTime"] + "</span>" +
                        "           <span class=\"right\">" + orderStatus["status"] + "</span>\n" +
                        "       </div>";
                    $(html).insertAfter(".cGrxj");
                });
            } else {
                let html = "<div class=\"styles__StyledOrderStatusDetailItem dPnPGf\">\n" +
                    "           <span class=\"left\">" + orderStatus["lastUpdatedTime"] + "</span>" +
                    "           <span class=\"right\">" + orderStatus["latestStatus"] + "</span>\n" +
                    "       </div>";
                $(html).insertAfter(".cGrxj");
            }


            let listOrderItem = order["order"]["listOrderDetail"];
            let itemClone = $(".styles__StyledOrderItems .items div:first-child");
            $.each(listOrderItem, function (index, item) {
                let row = itemClone.clone();
                row.find("img.image").attr("src", item["image"]);
                row.find(".info a.name").attr("href", "/products/" + item["productUrl"] + ".html").text(item["productName"]);
                row.insertAfter(".styles__StyledOrderItems .items div:first-child");
            });
            $(".styles__StyledOrderItems .items div:first-child").css("display", "none");
        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });
}

function getAllCouponUser() {
    $.ajax({
        url: "/api/admin/promotion/all",
        type: "GET",
        contentType: "application/json",
        success: function (data) {
            let itemClone = $("body > section > div.container > div > div:nth-child(1)");
            $.each(data, function (index, item) {
                console.log(item);
                let coupon = itemClone.clone();
                coupon.find("p.code > span").text(item["code"]);
                coupon.find("p.title").text(item["title"]);
                if (item.hasOwnProperty("startDate") && item.hasOwnProperty("endDate")) {
                    coupon.find("p.expires > span").text(item["endDate"]);
                } else {
                    coupon.find("p.expires").hide();
                }
                $("body > section > div.container > div").append(coupon);
            });
            itemClone.hide();
        },
        error: function () {
            alert("có lỗi xảy ra, không lấy được danh sách Coupon");
        }
    });
}

function getListOrderItem(orderId) {
    $.ajax({
        url: "/api/order/" + orderId + "/list-item",
        type: "GET",
        dataType: 'json',
        success: function (data) {
            let orderItems = data["orderItems"];
            let itemClone = $(".styles__StyledOrderItems .items div:first-child");
            $.each(orderItems, function (index, item) {
                let row = itemClone.clone();
                row.find("img.image").attr("src", "/images/products/" + item["image"]);
                row.find(".info a.name").attr("href", "/products/" + item["productUrl"] + ".html").text(item["productName"]);
                row.insertAfter(".styles__StyledOrderItems .items div:first-child");
            });
            $(".styles__StyledOrderItems .items div:first-child").css("display", "none");
        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });
}

function displayProduct(productUrl) {
    $.ajax({
        url: "/api/products/" + productUrl,
        type: "GET",
        dataType: 'json',
        success: function (res) {
            console.log(res);
            if (res["status"] === true) {
                let product = res["data"]["product"];
                // set image
                $.each(product["images"], function (index, item) {
                    let html = "<div class=\"single-slider\">\n" +
                        "           <img src='" + item + "'>\n" +
                        "        </div>";
                    $("div.quickview-slider-active-1").append(html);
                });
                $('.quickview-slider-active-1').owlCarousel({
                    items: 1,
                    autoplay: true,
                    autoplayTimeout: 5000,
                    smartSpeed: 400,
                    autoplayHoverPause: true,
                    nav: true,
                    loop: true,
                    merge: true,
                    dots: false,
                    navText: ['<i class=" ti-arrow-left"></i>', '<i class=" ti-arrow-right"></i>'],
                });

                $("div.breadcrumbs > div > div > div > div > ul > li:nth-child(3) > a").attr("href", "/products/" + product["productUrl"]).text(product["productName"]);
                $("#author").text(product["author"].replace("['", "").replace("']",""));
                $("#since").text(product["since"]);
                $("#rating_count").text(product["numberOfReviews"]);
                $("#amount").text(product["amount"]);
                $("div.modal-body > div > div:nth-child(2) > div").attr("product-url", product["productUrl"]);
                $("div.modal-body > div > div > div > div > h2").text(product["productName"]).attr("product-url", product["productUrl"]);
                $("#final-price").text("$" + product["finalPrice"]);
                $("#price").text("$" + product["price"]);
                $("#add-cart").attr("productUrl", product["productUrl"]);
                $("div.modal-body > div > div:nth-child(2) > div > div.quantity > div.add-to-cart > a.btn.min").attr("href", "/api/account/wishlist/add/" + product["id"]);
                $("#tabs-1 > div > h6").text(product["productName"]);
                // $("#tabs-1 > div > p").html(product["detailDescription"]);
                $("div.quickview-ratting-review > div > span").text("("+ product["rating"] + ")");
                if (product["amount"] <= 0) {
                    $("div.modal-body > div > div:nth-child(2) > div > div:nth-child(1) > div > span").hide();
                }
                // ratting
                if (parseInt(product["rating"]) > 0) {
                    for (let i = 0; i < parseInt(product["rating"]); i++) {
                        $("div.quickview-ratting-review > div.quickview-ratting-wrap > ul.reviews").append("<li class=\"yellow\"><i class=\"ti-star\"></i></li>");
                    }
                }
                if ((5 - parseInt(product["rating"])) > 0) {
                    for (let i = 0; i < (5 - parseInt(product["rating"])); i++) {
                        $("div.quickview-ratting-review > div.quickview-ratting-wrap > ul.reviews").append("<li><i class=\"ti-star\"></i></li>");
                    }
                }
            } else {
                // hoặc redirect về trang 404
                $("body > section > div").empty().append("<div class='center'>Không tìm thấy sản phẩm</div>");
            }
        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });
}

function displayReviewOfProduct(productUrl) {
    $.ajax({
        url: "/api/review/review_of_product?productUrl=" + productUrl,
        type: "GET",
        dataType: 'json',
        success: function (res) {
            console.log(res);
            if (res["status"] === true) {
                $("section.product-area.shop-sidebar.shop.section > div > div.row > div > div > ul > li:nth-child(2) > a > span").text("(" + res["data"].length + ")");

                let firstUl = $("#tabs-3 > div:nth-child(2) > div > div:nth-child(1)");
                $.each(res["data"], function (index, item) {
                    let rowClone = firstUl.clone();
                    rowClone.attr("data-id_comment", item["id"])
                    rowClone.find("div > h4 > span:nth-child(1)").text(item["reviewerName"]);
                    rowClone.find("div > h4 > span:nth-child(2)").text(item["createdAt"]);
                    rowClone.find("div > h4 > span:nth-child(3) > span.title").text(item["title"]);
                    rowClone.find("div > p.content").text(item["comment"]);

                    rowClone.find("div > div.like-and-review > span.like > span > span").text("(" + item["numbersOfLike"] + ")");
                    rowClone.find("div.reply-input").attr("id", "reply-review-" + item["id"]).hide();
                    rowClone.find("form > input.id-root-review").val(item["id"]);
                    rowClone.find(".list-reply-item").attr("id", "reply-items-list-" + item["id"]);
                    if (parseInt(item["rating"]) > 0) {
                        for (let i = 0; i < parseInt(item["rating"]); i++) {
                            rowClone.find("div > h4 > span:nth-child(3) > span.rating").append("<i class=\"yellow fa fa-star\"></i>");
                        }
                    }
                    if ((5 - parseInt(item["rating"])) > 0) {
                        for (let i = 0; i < (5 - parseInt(item["rating"])); i++) {
                            rowClone.find("div > h4 > span:nth-child(3) > span.rating").append("<i class=\"fa fa-star\"></i>");
                        }
                    }

                    if (item.hasOwnProperty("subReviews")) {
                        rowClone.find(".review-comment_sub-comments").show();
                        $.each(item["subReviews"], function (index, subItem) {
                            let html = "";
                            if (index === (item["subReviews"].length - 1)) {
                                html = "<div class=\"single-comment\" style='margin-bottom: 0px;'>\n";
                            } else {
                                html = "<div class=\"single-comment\">\n";
                            }
                            html += "<img src=\"https://via.placeholder.com/80x80\" alt=\"#\">\n" +
                                "       <div class=\"content\">\n" +
                                "           <h4>" +
                                "               <span>" + subItem["reviewerName"] + "</span>\n" +
                                "               <span>" + subItem["createdDate"] + "</span>" +
                                "           </h4>\n" +
                                "           <p>" + subItem["content"] + "</p>\n" +
                                "       </div>\n" +
                                "   </div>";
                            rowClone.find(".list-reply-item").append(html);
                        });
                        rowClone.find("div.view-reply-more > span").text(item["subReviews"].length);
                        rowClone.find("div.view-reply-less > span").text(item["subReviews"].length);
                        rowClone.find(".list-reply-item").hide();
                        rowClone.find("div.view-reply-more").show();
                        rowClone.find("div.view-reply-less").hide();

                    } else {
                        rowClone.find(".review-comment_sub-comments").hide();
                    }
                    rowClone.insertAfter(firstUl);
                });
                firstUl.remove();
            }
            else {
                $("#tabs-3").empty().append("<div class='col-12'>" +
                    "   <div class='reviews-empty' style='text-align: center; min-height: 160px;'>" +
                    "       <img src='/images/review.png' style='width: 100px;'>" +
                    "       <p style='#333; margin-top: 10px; font-size: 16px; font-weight: 500;'>Sản phẩm chưa có bình luận nào</p>" +
                    "   </div>" +
                    "</div>")
            }
        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });

}

function applyCoupon(couponCode) {
    $.ajax({
        url: "/api/checkout/apply-couponCode",
        type: "POST",
        data: JSON.stringify(couponCode),
        dataType: 'json',
        contentType: 'application/json',
        success: function (order) {
            $(".error-msg").remove();
            $(".order-details ul").empty();
            $(".order-details ul").append(
                "<li>Tổng các mặt hàng<span id=\"sub-total\">$" + order["subTotal"] + "</span></li>\n" +
                "<li>(+) Phí vận chuyển<span id=\"ship-fee\">$" + order["shipFee"] + "</span></li>\n" +
                "<li>(-) Giảm giá<span id=\"discount\">$" + order["discount"] + "</span></li>\n" +
                "<li class=\"last\">Tổng thanh toán\n" +
                "    <span id=\"total-cart\"$>$" + order["total"] + "</span>\n" +
                "</li>"
            );
        },
        error: function (xhr) {
            $(".coupon").after("<div class='error-msg' style='color: red; margin: 0px 30px;'>" + xhr.responseText + "</div>");
        }
    });
}

function getInfoPayment() {
    $.ajax({
        url: "/api/checkout/getInfo",
        type: "GET",
        contentType: "application/json",
        success: function (res) {
            console.log(res);
            if (res["status"] === true) {
                let listOrderItem = res["data"]["listOrderItem"];
                let firstItem = $("#payment > div > div.col-lg-7 > div.header > div > div > ul > li:nth-child(1)");
                $.each(listOrderItem, function(index, item) {
                    let itemClone = firstItem.clone();
                    itemClone.attr("url", item["product"]["productUrl"]);
                    itemClone.find("a.cart-img").attr("href", "/products/" + item["product"]["productUrl"]);
                    itemClone.find("h4 > a").attr("href", "/products/" + item["product"]["productUrl"]).text(item["product"]["title"]);
                    itemClone.find(".quantity").text(item["quantity"] + "x");
                    itemClone.find(".amount").text("$" + item["product"]["finalPrice"]);
                    itemClone.find("a > img").attr("src", item["product"]["mainImage"]);
                    itemClone.insertAfter(firstItem);
                });
                firstItem.remove();

                let order = res["data"]["order"];
                if (order.hasOwnProperty("address")) {
                    $(".contact-address .address .name").text(order["address"]["contactReceiver"]);
                    $(".contact-address .address .street").text(order["address"]["contactAddress"]);
                    $(".contact-address .address .phone").text("Điện thoại : " + order["address"]["contactPhone"]);
                } else {
                    $("#payment > div > div:nth-child(1) > div > div > div > div > a").removeAttr("data-toggle").removeAttr("data-target")
                        .text("thêm mới").attr("href", "/customer/address");
                    $("#payment > div > div:nth-child(1) > div > div > div > div.title").after("<p style='text-align: center; color: red; margin-top: 50px;'>Bạn chưa thêm địa chỉ giao hàng</p>");
                    $("#payment > div > div:nth-child(1) > div > div > div > div.address").remove();
                    $("#payment > div > div:nth-child(1) > div > div > div > div.form-group.note").remove();
                    $("#payment-order").closest("div").hide();
                }

                $(".shop.checkout .single-widget .content ul li span[id=\"sub-total\"]").text("$" + order["subTotal"]);
                $(".shop.checkout .single-widget .content ul li span[id=\"discount\"]").text("$" + order["discount"]);
                $(".shop.checkout .single-widget .content ul li span[id=\"ship-fee\"]").text("$" + order["shipFee"]);
                $(".shop.checkout .single-widget .content ul li span[id=\"total-cart\"]").text("$" + order["total"]);
                $("input[name='deliveryMethod']").each(function () {
                    if ($(this).val() === order["deliveryMethod"]) {
                        $(this).prop('checked', true);
                    }
                });
                $("input[name='paymentMethod']").each(function () {
                    if ($(this).val() === order["paymentMethod"]) {
                        $(this).prop('checked', true);
                    }
                });
            } else {
                alert(res["errMessage"]);
            }
        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });
}

function payment() {
    let data = {
        "address": {
            "contactReceiver": $(".contact-address .address .name").text(),
            "contactPhone": $(".contact-address .address .phone").text().replace("Điện thoại : ", ""),
            "contactAddress": $(".contact-address .address .street").text()
        },
        "note": $("form[id='payment']").find("textarea").val(),
        "subTotal": $(".shop.checkout .single-widget .content ul li span[id=\"sub-total\"]").text().replace("$",""),
        "promotion": $(".shop.checkout .single-widget .content ul li span[id=\"discount\"]").text().replace("$",""),
        "shipFee": $(".shop.checkout .single-widget .content ul li span[id=\"ship-fee\"]").text().replace("$",""),
        "total": $(".shop.checkout .single-widget .content ul li span[id=\"total-cart\"]").text().replace("$",""),
        "paymentMethod": $("input[name='paymentMethod']:checked").val()
    }
    // thah toán xong return về trang thông báo thanh toán đơn hàng thành công
    $.ajax({
        url: "/api/checkout/payment",
        type: "POST",
        data: JSON.stringify(data),
        contentType: 'application/json; charset=utf-8',
        success: function (res) {
            console.log(res);
            $("#myModal").delay(1000).fadeOut();
            localStorage.setItem('orderId', res["data"]["orderId"]);
            localStorage.setItem('orderValue', res["data"]["orderValue"]);
            window.location = res["data"]["urlRedirect"];
        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });

}

function replyReview(data) {
    $.ajax({
        url: "/api/review/reply",
        type: "POST",
        data: JSON.stringify(data),
        dataType: 'json',
        contentType: 'application/json',
        success: function (result) {
            console.log(result);

        let html = "<div class=\"single-comment\">\n" +
            "           <img src=\"https://via.placeholder.com/80x80\" alt=\"#\">\n" +
            "           <div class=\"content\">\n" +
            "               <h4>" +
            "                   <span>" + result["reviewerName"] + "</span>\n" +
            "                   <span>" + result["createdDate"] + "</span></h4>\n" +
            "                   <p>" + result["content"] + "</p>\n" +
            "           </div>\n" +
            "      </div>";
            let numberOfReview = $("#reply-items-list-" + data["parentId"]).siblings(".view-reply-more").find('span').text();
            console.log(numberOfReview);
            if (numberOfReview === "") {
                numberOfReview = 0;
                console.log(numberOfReview);
            }
            $("div.reply-input").hide();
            $("#reply-items-list-" + data["parentId"]).siblings(".view-reply-more").find('span').text(parseInt(numberOfReview) + 1);
            $("#reply-items-list-" + data["parentId"]).siblings(".view-reply-less").find('span').text(parseInt(numberOfReview) + 1);
            $(html).delay(500).prependTo("#reply-items-list-" + data["parentId"]).hide().slideDown().fadeIn();
            $("#reply-items-list-" + data["parentId"]).show();
            $("#reply-items-list-" + data["parentId"]).siblings(".view-reply-more").hide();
            $("#reply-items-list-" + data["parentId"]).siblings(".view-reply-less").show();
            $("#reply-items-list-" + data["parentId"]).show();
        },
        error: function (xhr) {
            alert(xhr.responseText);
        }
    });
}

function likeComment(thisLikeButton, data) {
    $.ajax({
        url: "/api/review/likeComment",
        data: data,
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        success: function (result) {
            thisLikeButton.find("span > span").text("(" + result + ")");
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
