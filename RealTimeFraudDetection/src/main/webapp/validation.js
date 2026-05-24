function validateAmount() {
    var amount = document.getElementById("amount").value;

    if (amount <= 0) {
        alert("Amount must be greater than zero");
        return false;
    }

    if (amount > 100000) {
        alert("Amount exceeds Paytm transaction limit");
        return false;
    }
    return true;
}
