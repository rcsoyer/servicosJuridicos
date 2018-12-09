import { Injectable } from '@angular/core';
import * as _ from 'lodash';
import * as R from 'ramda';

@Injectable()
export class FormatDateUtils {
    getUnformatedDate(dateValue: any) {
        const notEmpty = _.negate(_.isEmpty);
        return R.when(notEmpty, () => {
            return {
                date: {
                    year: dateValue.year,
                    month: dateValue.month,
                    day: dateValue.day
                }
            };
        })(dateValue);
    }
}
