import { Injectable } from '@angular/core';
import { NgbDateParserFormatter, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';

@Injectable()
export class NgbDateCustomParserFormatter extends NgbDateParserFormatter {
  readonly DELIMITER = '/';

  parse(value: string): NgbDateStruct | null {
    if (value) {
      const date = value.split(this.DELIMITER);
      return {
        day: parseInt(date[0], 10),
        month: parseInt(date[1], 10),
        year: parseInt(date[2], 10),
      };
    }
    return null;
  }

  format(date: NgbDateStruct | null): string {
    if (date) {
      const day = date.day.toFixed(0).padStart(2, '0');
      const month = date.month.toFixed(0).padStart(2, '0');
      const year = date.year.toFixed(0).padStart(2, '0');
      return day + this.DELIMITER + month + this.DELIMITER + year;
    }
    return '';
  }
}
