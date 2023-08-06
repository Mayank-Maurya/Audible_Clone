import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'toHours'
})
export class ToHoursPipe implements PipeTransform {

  transform(minutes: number): unknown {
    const hours = Math.floor(minutes/60);
    return `${hours}`
  }

}
