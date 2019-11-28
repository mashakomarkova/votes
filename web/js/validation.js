function checkDate() {
    var first_1 = document.getElementById('check_in').value;
    var second_2 = document.getElementById('check_out').value;

    var a = Date.parse(first_1);
    var b = Date.parse(second_2);

    if (a.toLocaleString() > b.toLocaleString() || a.toLocaleString() === b.toLocaleString()) {
        document.getElementById('check_out').value = "";
    }
}

function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#image').attr('src', e.target.result);
        }

        reader.readAsDataURL(input.files[0]);
    }
}



