$('#send').on('click', send);

function send() {
    var message = $('textarea').val();
    console.log(message);
    if(message){
    $('.chatbox').append('<div class="my-bubble bubble">'+message+'</div>');
    $('textarea').val('');
    }
}