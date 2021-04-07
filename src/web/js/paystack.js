const paymentForm = document.getElementById('checkout-form');

let transactionID = document.getElementById("transaction-id");
const emailAddress = document.getElementById("email-address").value;
const fare = document.getElementById("fare").value;

paymentForm.addEventListener("submit", payWithPaystack, false);

function payWithPaystack(e) {
    e.preventDefault();

    let handler = PaystackPop.setup({
        key: "pk_test_6c0e240a38094183283cbd3b0491806f831f97db",
        email: emailAddress,
        amount: fare * 100,

        onClose: function() {
            alert("Window closed.");
        },

        callback: function(response) {            
            paymentForm.removeEventListener("submit", payWithPaystack, false);
            enableRerservationConfirmation();
            transactionID.value = response.reference;
        }   //  end of callback
    });

    handler.openIframe();
}   //  end of payWithPaystack()

function enableRerservationConfirmation() {
    paymentForm.setAttribute("action", "confirm-reservation");
    paymentForm.setAttribute("method", "POST");
}   //  end of enableRerservationConfirmation()