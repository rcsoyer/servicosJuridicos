import { NgModule } from '@angular/core';
import { WhitespaceValidator } from './whitespace.directive.validator';

@NgModule({
    declarations: [WhitespaceValidator],
    exports: [WhitespaceValidator]
})
export class WhitespaceModule {}
