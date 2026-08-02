// Placeholder for shared client-side scripts.
// Add small enhancements here as needed (e.g. form tweaks, confirmations).

function updateAttendanceDate() {

    const eventSelect = document.getElementById("eventSelect");
    const selectedOption = eventSelect.options[eventSelect.selectedIndex];

    const eventDate = selectedOption.getAttribute("data-date");

    if (eventDate) {
        document.getElementById("attendanceDate").value = eventDate;
    }
}

// Automatically set date when editing attendance
document.addEventListener("DOMContentLoaded", function () {

    const eventSelect = document.getElementById("eventSelect");

    if (eventSelect && eventSelect.value) {
        updateAttendanceDate();
    }

});

function validateAvailableQuantity() {

    const quantity = document.getElementById("quantity");
    const available = document.getElementById("availableQuantity");

    if (!quantity || !available) return;

    const total = parseInt(quantity.value) || 0;
    const availableQty = parseInt(available.value) || 0;

    if (availableQty > total) {
        alert("Available quantity cannot be greater than total quantity.");
        available.value = total;
    }
}

document.addEventListener("DOMContentLoaded", function () {

    const quantity = document.getElementById("quantity");
    const available = document.getElementById("availableQuantity");

    if (quantity && available) {
        quantity.addEventListener("input", validateAvailableQuantity);
        available.addEventListener("input", validateAvailableQuantity);
    }
});