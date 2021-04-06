const paymentForm = document.getElementById('checkout-form');

const transactionID = document.getElementById("transaction-id").value;
const busID = document.getElementById("bus-id").value;
const station = document.getElementById("station").value;
const destination = document.getElementById("destination").value;
const seatNumber = document.getElementById("seat-number").value;
const fare = document.getElementById("fare").value;
const reservationDate = document.getElementById("reservation-date").value;
const arrivalDate = document.getElementById("arrival-date").value;

paymentForm.addEventListener("submit", payWithPaystack, false);

function payWithPaystack(e) {
    e.preventDefault();

    let handler = PaystackPop.setup({
        key: "pk_test_6c0e240a38094183283cbd3b0491806f831f97db",
        email: document.getElementById("email-address").value,
        amount: fare * 100,
        ref: transactionID,
        onClose: function() {
            alert('Window closed.');
        },
        callback: function(response) {
            window.location = "http://localhost:8080/BusReservationSystem/confirm-reservation?transaction-id=" + response.reference +
            "&bus-id=" + busID + "&station=" + station + "&destination=" + destination + "&seat-number=" + seatNumber +
            "&fare=" + fare + "&reservation-date=" + reservationDate + "&arrival-date=" + arrivalDate;
        }
    });

    handler.openIframe();
}