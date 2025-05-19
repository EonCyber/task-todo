import { DateTime } from 'luxon';

export function toDateStringInGMT3(date: Date): string {
  return DateTime.fromJSDate(date)
    .setZone('America/Sao_Paulo') // GMT-3
    .toFormat('yyyy-MM-dd');
}

export function meargeDateToTime(date:Date, timeDate: Date): Date { 
  const zone = 'America/Sao_Paulo';
  // Inputs
  const selectedDate = DateTime.fromJSDate(date, { zone }).startOf('day'); // strips time
  const selectedTime = DateTime.fromJSDate(timeDate, { zone });

  const mergedDateTime = selectedDate.set({
    hour: selectedTime.hour,
    minute: selectedTime.minute,
    second: selectedTime.second,
    millisecond: selectedTime.millisecond
  });

  return mergedDateTime.toJSDate(); 

}

export function formatDateToMonthDay(date: Date): string {
  const options: Intl.DateTimeFormatOptions = { month: 'long', day: 'numeric' };
  const formatted = date.toLocaleDateString('en-US', options);
  return `${formatted}`;
}

export function isItToday(date: Date): boolean {
  const zone = 'America/Sao_Paulo';
  const inputDate = DateTime.fromJSDate(date, { zone });
  const today = DateTime.now().setZone(zone);

  return inputDate.hasSame(today, 'day');

}