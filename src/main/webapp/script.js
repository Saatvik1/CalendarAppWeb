const date = new Date();

const renderCalendar = () => {
  date.setDate(1);

  const monthDays = document.querySelector(".days");

  const lastDay = new Date(
    date.getFullYear(),
    date.getMonth() + 1,
    0
  ).getDate();

  const prevLastDay = new Date(
    date.getFullYear(),
    date.getMonth(),
    0
  ).getDate();

  const firstDayIndex = date.getDay();

  const lastDayIndex = new Date(
    date.getFullYear(),
    date.getMonth() + 1,
    0
  ).getDay();

  const nextDays = 7 - lastDayIndex - 1;

  const months = [
    "January",
    "February",
    "March",
    "April",
    "May",
    "June",
    "July",
    "August",
    "September",
    "October",
    "November",
    "December",
  ];

  function getMonthString(compare){
  return months.findIndex("June") + 1;
  }

  document.querySelector(".date h1").innerHTML = months[date.getMonth()];

  document.querySelector(".date p").innerHTML = new Date().toDateString();

  let days = "";

  for (let x = firstDayIndex; x > 0; x--) {
    days += `<div class="prev-date" id= "${x} prev">${prevLastDay - x + 1}</div>`;
  }

  for (let i = 1; i <= lastDay; i++) {
    if (
      i === new Date().getDate() &&
      date.getMonth() === new Date().getMonth()
    ) {
      days += `<div class="today" id= "${i} norm">${i}</div>`;
    } else {
      days += `<div id= "${i} norm">${i}</div>`;
    }
  }

  for (let j = 1; j <= nextDays; j++) {
    days += `<div class="next-date" id= "${j} next">${j}</div>`;
    monthDays.innerHTML = days;
  }
};

document.querySelector(".prev").addEventListener("click", () => {
  date.setMonth(date.getMonth() - 1);
  renderCalendar();
});

document.querySelector(".next").addEventListener("click", () => {
  date.setMonth(date.getMonth() + 1);
  renderCalendar();
});


let dayIdClicked = "";

function stringGenerator1(monthCon, dateCon){
let finalString = "";

finalString += date.getFullYear() + "-";

 if(monthCon.length == 2){
 finalString += monthCon + "-";
 } else {
 finalString += "0" + monthCon + "-";
 }

if(dateCon.length == 2){
finalString += dateCon;
} else {
finalString += "0" + dateCon;
}

return finalString;
}

function stringGenerator2(monthCon, dateCon){
let finalString = "";

finalString += date.getFullYear() + "-";


var integerTestDate = parseInt(dateCon, 10);
var integerTestMonth = parseInt(monthCon, 10);

if(integerTestDate + 1 > new Date(date.getFullYear(),date.getMonth() + 1,0).getDate()){
dateCon = 1;
integerTestMonth += 1;
monthCon = integerTestMonth + "";
} else {
integerTestDate += 1;
dateCon = integerTestDate + "";
}

 if(monthCon.length == 2){
 finalString += monthCon + "-";
 } else {
 finalString += "0" + monthCon + "-";
 }

if(dateCon.length == 2){
finalString += dateCon;
} else {
finalString += "0" + dateCon;
}

return finalString;
}

const onClick = (event) => {
    let dayIdClicked = event.srcElement.id;
    let popup = document.getElementById("popup");
    popup.classList.add("open-popup");
    let startDate = document.getElementById("set-event-start-date");
    let endDate = document.getElementById("set-event-end-date");

    let dayInput = dayIdClicked.replace(" norm", "");
    let monthInput = 0;

  let compare = document.getElementById("rips").innerText;


const months = [
    "JANUARY",
    "FEBRUARY",
    "MARCH",
    "APRIL",
    "MAY",
    "JUNE",
    "JULY",
    "AUGUST",
    "SEPTEMBER",
    "OCTOBER",
    "NOVEMBER",
    "DECEMBER",
  ];

 monthInput = months.findIndex(month => month === compare) + 1;

  stringGenerator1(monthInput,dayInput);

  startDate.value = stringGenerator1(monthInput, dayInput);
  endDate.value = stringGenerator2(monthInput, dayInput);
}

document.querySelector(".days").addEventListener("click", onClick);

renderCalendar();

//Display the year next to the month
//Fix so that if clicked off a date it returns as null. and set it to the current date and the next date.
//Add so that if user clicks on next months date the calendar switches to the next month.