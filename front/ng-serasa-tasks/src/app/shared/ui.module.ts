import { NgModule } from "@angular/core";
import { ButtonModule } from 'primeng/button';

import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';

import { MenubarModule } from 'primeng/menubar';
import { MenuModule } from 'primeng/menu';

import { AvatarModule } from 'primeng/avatar';
import { AvatarGroupModule } from 'primeng/avatargroup';

import { TagModule } from 'primeng/tag';

import { SkeletonModule } from 'primeng/skeleton';

import { CardModule } from 'primeng/card';

import { DialogModule } from 'primeng/dialog';

import { DividerModule } from 'primeng/divider';
import { ConfirmDialogModule } from 'primeng/confirmdialog';

import { SelectModule } from 'primeng/select';
import { FloatLabelModule } from 'primeng/floatlabel';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';

import { DatePickerModule } from 'primeng/datepicker';

import { MessageModule } from 'primeng/message';
import { ToastModule } from 'primeng/toast';
import { RippleModule } from 'primeng/ripple';
import { PaginatorModule } from 'primeng/paginator';
import { CheckboxModule } from 'primeng/checkbox';

@NgModule({
    exports: [
        ButtonModule,
        IconFieldModule,
        InputIconModule,
        MenubarModule,
        MenuModule,
        AvatarModule,
        AvatarGroupModule,
        DatePickerModule,
        SkeletonModule,
        TagModule,
        CardModule,
        DialogModule,
        DividerModule,
        ConfirmDialogModule,
        SelectModule,
        FloatLabelModule,
        InputTextModule,
        PasswordModule,
        MessageModule,
        ToastModule,
        RippleModule,
        PaginatorModule,
        CheckboxModule

    ]
})
export class UiModule {}