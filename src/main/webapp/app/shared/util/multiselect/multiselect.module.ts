import { NgModule } from '@angular/core';
import { MultiSelectValidator } from './multiselect.directive.validator';
import { MultiSelectSettings } from './multiselect.settings';
import { SingleSelectSettings } from './singleselect.settings';
import { OptionsTexts } from './options.texts';

@NgModule({
    declarations: [MultiSelectValidator],
    exports: [MultiSelectValidator],
    providers: [MultiSelectSettings, SingleSelectSettings, OptionsTexts]
})
export class MultiSelectModule {}
