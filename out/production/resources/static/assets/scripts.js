$(function() {
    // Side Bar Toggle
    $('.hide-sidebar').click(function() {
	  $('#sidebar').hide('fast', function() {
	  	$('#content').removeClass('span9');
	  	$('#content').addClass('span12');
	  	$('.hide-sidebar').hide();
	  	$('.show-sidebar').show();
	  });
	});

	$('.show-sidebar').click(function() {
		$('#content').removeClass('span12');
	   	$('#content').addClass('span9');
	   	$('.show-sidebar').hide();
	   	$('.hide-sidebar').show();
	  	$('#sidebar').show('fast');
	});
});

$('.nav-list').find('a').each(function () {
	//  || document.location.href.search(this.href) >= 0
    if (this.href == document.location.href) {
        $(this).parent().addClass('active'); // this.className = 'active';
		console.info($(this).parent());
    }
    console.info("123456");
});

// model居中
$('#userCreateModal').on('show.bs.modal', function (e) {
    $(this).css('display', 'block');
    var modalHeight=$(window).height() / 2 - $('#userCreateModal .modal-dialog').height() / 2;
    console.info(modalHeight);
    $(this).find('.modal-dialog').css({
        'margin-top': modalHeight
    });
});


function getCookie(name) {
    var cookieValue = null;
    if (document.cookie && document.cookie != '') {
        var cookies = document.cookie.split(';');
        for (var i = 0; i < cookies.length; i++) {
            var cookie = jQuery.trim(cookies[i]);
            if (cookie.substring(0, name.length + 1) == (name + '=')) {
                cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                break;
            }
        }
    }
    return cookieValue;
}

function csrfSafeMethod(method) {
    return (/^(GET|HEAD|OPTIONS|TRACE)$/.test(method));
}


function onclickUserEdit(userId) {
	if(userId<=0){
		return;
	}

	console.info(userId);

   /* $.ajaxSetup({
        beforeSend: function (xhr, settings) {
            var csrftoken = getCookie('csrftoken');
            if (!csrfSafeMethod(settings.type) && !this.crossDomain) {
                xhr.setRequestHeader("X-CSRFToken", csrftoken);
            }
        }
    });

	$.ajax({
		url: '/caricature/manage/user_edit',
		type: 'GET',
		data: {'id': userId},
		dataType: 'json',
		success: function (response) {
			var user = JSON.parse(response.editUser);
        }
	})*/
}