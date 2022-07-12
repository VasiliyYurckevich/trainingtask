let forms = document.querySelector('form');
forms.addEventListener('submit', function(){
    let btn = this.querySelector("input[type=submit], button[type=submit]");
    btn.disabled = true;
});