import { WebPlugin } from '@capacitor/core';
import { GlocellPosModulePlugin } from './definitions';
export declare class GlocellPosModuleWeb extends WebPlugin implements GlocellPosModulePlugin {
    constructor();
    getSerial(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
    print(options: {
        ReceiptText: string;
        ReceiptLogo: string;
    }): Promise<{
        results: any[];
    }>;
}
declare const GlocellPosModule: GlocellPosModuleWeb;
export { GlocellPosModule };
