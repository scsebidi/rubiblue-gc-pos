import { WebPlugin } from '@capacitor/core';
import { GlocellPosModulePlugin } from './definitions';

export class GlocellPosModuleWeb extends WebPlugin implements GlocellPosModulePlugin {
    constructor() {
        super({
            name: 'GlocellPosModule',
            platforms: ['web'],
        });
    }

    async getSerial(options: { value: string }): Promise<{ value: string }> {
        console.log('getSerial', options);
        return options;
    }

    async print(options: { ReceiptText: string,ReceiptLogo: string }): Promise<{ results: any[] }> {
        console.log('print string: ', options.ReceiptText);
        console.log('print ReceiptLogo: ', options.ReceiptLogo);
        return {
            results: [{
                firstName: 'Dummy',
                lastName: 'Entry',
                telephone: '123456'
            }]
        };
    }
}

const GlocellPosModule = new GlocellPosModuleWeb();

export { GlocellPosModule };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(GlocellPosModule);
