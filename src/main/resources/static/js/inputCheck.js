function check() {
    var num = document.getElementById("inputAmount").value
    var button = document.getElementById("subBtn")
    if (num.trim().length === 0) {
        button.disabled = true
    } else {
        button.disabled = false
    }
}