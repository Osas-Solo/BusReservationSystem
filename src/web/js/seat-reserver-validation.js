let arrivalDateInput = document.getElementById("arrival-date");

let today = new Date();
let currentYear = today.getFullYear();
let currentMonth = today.getMonth() + 1;
let currentDay = today.getDate();

today = currentYear + "-";
today += (currentMonth < 10) ? ("0" + currentMonth) : currentMonth;
today += "-";
today += (currentDay < 10) ? ("0" + currentDay) : currentDay;

arrivalDateInput.setAttribute("min", today);