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
});


// model居中
/*$('#userCreateModal').on('show.bs.modal', function (e) {
    $(this).css('display', 'block');
    var modalHeight=$(window).height() / 2 - $('#userCreateModal .modal-dialog').height() / 2;
    $(this).find('.modal-dialog').css({
        'margin-top': modalHeight
    });
});*/

//关闭模态框时清空数据
$('body').on('hidden.bs.modal', '.modal', function (){
    // $(this).removeData('bs.modal');
    $(':input').not(':button,:submit,:reset').val('').removeAttr('checked').removeAttr('checked');
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


     $.ajax({
         url: '/caricature/manage/user_edit',
         type: 'GET',
         data: {'id': userId},
         dataType: 'json',
         success: function (result) {
            var user = result.user;
            $('#id').val(user.id);
            $('#username').val(user.username);
            $('#email').val(user.email);
            $('#selectRoles').val(user.roles)
         }
     });
}


function onclickUserHeader(id) {
    console.info(id)
    if($.isEmptyObject(id)){
        console.info("我最帅");

        $.ajax({
            url: '/caricature/manage/user_header',
            type: 'GET',
            data: {'id': id},
            dataType: 'json',
            success: function (result) {
                var user = result.user;
                console.info(user);
                document.getElementById("myModalLabel").textContent = user.username + "(头像)";
                document.getElementById("userHeader").src = user.header;
                // $('#userHeader').src(user.header);
            }
        });
    }
}

function onclickPictureEdit(pictureId) {
    if(pictureId<=0){
        return;
    }

    $.ajax({
        url: '/caricature/manage/picture_edit',
        type: 'GET',
        data: {'id': pictureId},
        dataType: 'json',
        success: function (result) {
            var caricature = result.caricature;
            $('#id').val(caricature.id);
            $('#name').val(caricature.name);
            $('#author').val(caricature.author);
            $('#title').val(caricature.title);
            $('#url').val(caricature.url);
            $('#message').val(caricature.message);
            $('#title').val(caricature.title);
            $('#selectGenre').val(caricature.genre);
            $("#isAction").prop("checked", caricature.isAction);
        }
    });
}

function onclickCaricatureCover(path) {
    $("#coverCaricature").attr('src', path);
}