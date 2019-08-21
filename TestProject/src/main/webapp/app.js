window.addEventListener('load', e=>{
    if('serviceWorker' in  navigator){
        try{
            navigator.serviceWorker.register('sw.js');
            console.log('sW registered');
         }
         catch(error){
             console.log('cant register sW')
         }
    }

});