

const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');

function Start() {
    $('.registr_or_login').css({ "display": "flex" }, { "justify-content": "center" });
    $('.container').css({ "display": "block" });
    $('.container1').css({ "display": "none" });
}

$('.left_side a').on('click', function () {
    $('.registr_or_login').css({ "display": "none" });
    $('.container1').css({ "display": "flex" }, { "justify-content": "center" });
});

$('.load').on('click', function () {
    $('.load_doc').trigger('click');
});

$('.load_doc').on('change', function (e) {
    let tar = e.target.files[0].name;
    $('.upload_fileName').text(tar);
});

signUpButton.addEventListener('click', () => {
    container.classList.add("right-panel-active");
});

signInButton.addEventListener('click', () => {
    container.classList.remove("right-panel-active");
});


$("#SignUpForm").submit(function (e) {
    e.preventDefault();
    const inputData = $(e.currentTarget).serializeArray();
    console.log('Serialyze array');
    console.log(inputData);

    let input = {};
    inputData.map(({ name, value }) => input[name] = value);
    console.log(input);

    $.post("/RegisterServlet", { ...input }).done(result => {
        if (!result) {
            return;
        }
        let { message } = result;

        switch (message) {
            case 'Email is already exist!':
                Swal.fire({
                    title: 'Opps!',
                    text: message,
                    icon: 'warning',
                    timer: 1500
                });
                $('#signIn').trigger('click');

                break;
            case 'Successfully signed up':
                Swal.fire(
                    'Good job!',
                    message,
                    'success'
                );

                break;
            case 'Password is not valid...':
                Swal.fire({
                    title: 'Opps!',
                    text: message,
                    icon: 'warning',
                    timer: 1500
                });
                break;
            case 'Email is not valid...':
                Swal.fire({
                    title: 'Opps!',
                    text: message,
                    icon: 'warning',
                    timer: 1500
                });
                $('.email').val('').css({ "border-bottom": "2px solid red" });
                break;
            case 'Input cannot be null..':
                Swal.fire({
                    title: 'Opps!',
                    text: message,
                    icon: 'warning',
                    timer: 1500
                });
                break;
        }
        $("#signIn").trigger('click');
    });
});


$("#SignInForm").submit(function (e) {
    e.preventDefault();
    const inputData = $(e.currentTarget).serializeArray();

    let input = {};
    $.each(inputData, (key, { name, value }) => input[name] = value);


    $.post("/LoginServlet", { ...input }).done(registerRes => {
    }).done(result => {

        if (!result) {
            return;
        }
        let { message, url } = result;

        if (url !== null) {
            window.location.href = url;
        } else {
            switch (message) {
                case "Invalid password...":
                    Swal.fire({
                        position: 'center',
                        title: 'Opps!',
                        text: message,
                        icon: 'warning',
                    });
                    $('.pass_input').val('').css({ "border-bottom": "2px solid red" });
                    break;

                case "There's no much email...":
                    Swal.fire({
                        position: 'center',
                        title: 'Opps!',
                        text: message,
                        icon: 'warning',
                    });
                    $('.email_input').val('').css({ "border-bottom": "2px solid red" });
                    break;
                case "This user is blocked..":
                    Swal.fire({
                        position: 'center',
                        title: 'Opps!',
                        text: message,
                        icon: 'warning',
                    });
                    break;
                case 'Email is not valid...':
                    Swal.fire({
                        title: 'Opps!',
                        text: message,
                        icon: 'warning',
                        timer: 1500
                    });
                    break;
                case 'Input cannot be null..':
                    Swal.fire({
                        title: 'Opps!',
                        text: message,
                        icon: 'warning',
                        timer: 1500
                    });
                    break;
            }
        }

    });
});

function showPass() {
    $(this).toggleClass("fa-eye fa-eye-slash");
    var $pass = $(".pass_input");
    $('.fa-eye-slash').hide();
    if ($pass.attr('type') === 'password') {
        $pass.attr('type', 'text');
        $('.fa-eye-slash').hide();
        $('.fa-eye').show();
    } else {
        $pass.attr('type', 'password');
        $('.fa-eye').hide();
        $('.fa-eye-slash').show();
    }
}