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