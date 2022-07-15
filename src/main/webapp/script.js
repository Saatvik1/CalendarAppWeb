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


  document.querySelector(".date h1").innerHTML = months[date.getMonth()] + " " + date.getFullYear();

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
monthCon = monthCon + "";
monthCon.replace(" " + date.getFullYear(),"");
dateCon = dateCon + "";

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
    "JANUARY " + date.getFullYear(),
    "FEBRUARY "+ date.getFullYear(),
    "MARCH "+ date.getFullYear(),
    "APRIL "+ date.getFullYear(),
    "MAY "+ date.getFullYear(),
    "JUNE "+ date.getFullYear(),
    "JULY "+ date.getFullYear(),
    "AUGUST "+ date.getFullYear(),
    "SEPTEMBER"+ date.getFullYear(),
    "OCTOBER "+ date.getFullYear(),
    "NOVEMBER "+ date.getFullYear(),
    "DECEMBER "+ date.getFullYear(),
  ];

monthInput = months.findIndex(month => month === compare) + 1;
 if(dayIdClicked === ""){
startDate.value = stringGenerator1(new Date().getMonth() + 1, new Date().getDate());
endDate.value = stringGenerator2(new Date().getMonth() + 1, new Date().getDate());
} else if(dayIdClicked.includes("next")){
date.setMonth(date.getMonth() + 1);
  renderCalendar();
} else if(dayIdClicked.includes("prev")) {
date.setMonth(date.getMonth() - 1);
  renderCalendar();
} else {
startDate.value = stringGenerator1(monthInput, dayInput);
endDate.value = stringGenerator2(monthInput, dayInput);
}
  }

  const onClickPopup = (event) => {
  let buttonIdClicked = event.srcElement.id;
  let moreInfo = document.getElementById("moreInfoClass");

  if(buttonIdClicked === "more-options"){
  moreInfo.classList.add("open-moreInfoPopup");

  }

  }

  function printUserEmail(userEmail){
  console.log(userEmail);

  }


document.querySelector(".days").addEventListener("click", onClick);
document.querySelector(".popup").addEventListener("click", onClickPopup);

renderCalendar();



//Add the rest of the google like options when clicked on a date.

//When double click a date, redirect the page to a in the day hourly view.